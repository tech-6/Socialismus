package me.whereareiam.socialismus.adapter.config.provider;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import me.whereareiam.socialismus.adapter.config.management.ConfigLoader;
import me.whereareiam.socialismus.api.Reloadable;
import me.whereareiam.socialismus.api.input.registry.Registry;
import me.whereareiam.socialismus.api.model.CommandEntity;
import me.whereareiam.socialismus.api.model.config.Commands;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

@Singleton
public class CommandsProvider implements Provider<Map<String, CommandEntity>>, Registry<Map<String, CommandEntity>>, Reloadable {
    private final Path dataPath;
    private final ConfigLoader configLoader;

    private Map<String, CommandEntity> commands;

    @Inject
    public CommandsProvider(@Named("dataPath") Path dataPath, ConfigLoader configLoader, Registry<Reloadable> registry) {
        this.dataPath = dataPath;
        this.configLoader = configLoader;

        registry.register(this);
    }

    @Override
    public Map<String, CommandEntity> get() {
        if (commands != null) return commands;

        load();

        return commands;
    }

    @Override
    public void reload() {
        load();
    }

    private void load() {
        commands = new HashMap<>(configLoader.load(dataPath.resolve("commands"), Commands.class).getCommands());
    }

    @Override
    public void register(Map<String, CommandEntity> commands) {
        this.commands.put(commands.keySet().iterator().next(), commands.values().iterator().next());
    }
}