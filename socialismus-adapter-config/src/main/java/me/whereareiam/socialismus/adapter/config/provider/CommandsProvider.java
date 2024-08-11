package me.whereareiam.socialismus.adapter.config.provider;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import me.whereareiam.socialismus.adapter.config.management.ConfigLoader;
import me.whereareiam.socialismus.api.Reloadable;
import me.whereareiam.socialismus.api.input.registry.Registry;
import me.whereareiam.socialismus.api.model.config.Commands;

import java.nio.file.Path;

@Singleton
public class CommandsProvider implements Provider<Commands>, Reloadable {
    private final Path dataPath;
    private final ConfigLoader configLoader;

    private Commands commands;

    @Inject
    public CommandsProvider(@Named("dataPath") Path dataPath, ConfigLoader configLoader, Registry<Reloadable> registry) {
        this.dataPath = dataPath;
        this.configLoader = configLoader;

        registry.register(this);
    }

    @Override
    public Commands get() {
        if (commands != null) return commands;

        load();

        return commands;
    }

    @Override
    public void reload() {
        load();
    }

    private void load() {
        commands = configLoader.load(dataPath.resolve("commands"), Commands.class);
    }
}