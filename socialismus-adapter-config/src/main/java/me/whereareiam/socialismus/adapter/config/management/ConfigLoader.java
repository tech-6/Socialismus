package me.whereareiam.socialismus.adapter.config.management;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.Getter;
import me.whereareiam.socialismus.api.exception.ConfigLoadException;
import me.whereareiam.socialismus.api.output.DefaultConfig;
import me.whereareiam.socialismus.api.output.config.ConfigurationLoader;
import me.whereareiam.socialismus.api.type.ConfigurationType;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Map;

@Getter
@Singleton
public class ConfigLoader implements ConfigurationLoader {
    private final ConfigurationType configurationType;
    private final ObjectMapper objectMapper;

    private final Map<Class<?>, DefaultConfig<?>> templates;

    private final ConfigSaver configSaver;
    private final ConfigMerger configMerger;

    @Inject
    public ConfigLoader(ConfigManager configManager, Map<Class<?>, DefaultConfig<?>> templates,
                        ObjectMapper objectMapper, ConfigSaver configSaver, ConfigMerger configMerger) {
        this.configurationType = configManager.getConfigurationType();
        this.objectMapper = objectMapper;
        this.templates = templates;
        this.configSaver = configSaver;
        this.configMerger = configMerger;
    }

    @SuppressWarnings("unchecked")
    public <T> T load(Path path, Class<T> clazz) {
        path = path.resolveSibling(path.getFileName() + configurationType.getExtension());

        T config;
        try {
            config = objectMapper.readValue(path.toFile(), clazz);
        } catch (FileNotFoundException e) {
            config = ((DefaultConfig<T>) templates.get(clazz)).getDefault();
            configSaver.save(path, config);
        } catch (Exception e) {
            throw new ConfigLoadException("Failed to load configuration", e);
        }

        T defaultConfig = ((DefaultConfig<T>) templates.get(clazz)).getDefault();
        configMerger.merge(config, defaultConfig);
        configSaver.save(path, config);

        return config;
    }

    @Override
    public <T> T load(InputStream stream, Class<T> clazz) {
        T config;
        try {
            config = objectMapper.readValue(stream, clazz);
        } catch (Exception e) {
            throw new ConfigLoadException("Failed to load configuration", e);
        }

        return config;
    }
}