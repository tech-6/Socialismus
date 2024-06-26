package me.whereareiam.socialismus.adapter.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.Getter;
import me.whereareiam.socialismus.api.output.DefaultConfig;
import me.whereareiam.socialismus.api.type.ConfigurationType;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.Map;

@Getter
@Singleton
public class ConfigLoader {
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
			throw new RuntimeException("Failed to load configuration", e);
		}

		T defaultConfig = ((DefaultConfig<T>) templates.get(clazz)).getDefault();
		configMerger.merge(config, defaultConfig);
		configSaver.save(path, config);

		return config;
	}
}