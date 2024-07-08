package me.whereareiam.socialismus.adapter.config.provider;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.name.Named;
import me.whereareiam.socialismus.adapter.config.management.ConfigLoader;
import me.whereareiam.socialismus.api.Reloadable;
import me.whereareiam.socialismus.api.input.Registry;
import me.whereareiam.socialismus.api.model.config.settings.Settings;

import java.nio.file.Path;

public class SettingsProvider implements Provider<Settings>, Reloadable {
    private final Path dataPath;
    private final ConfigLoader configLoader;
    private Settings settings;

    @Inject
    public SettingsProvider(@Named("dataPath") Path dataPath, ConfigLoader configLoader, Registry<Reloadable> registry) {
        this.dataPath = dataPath;
        this.configLoader = configLoader;

        registry.register(this);
    }

    @Override
    public Settings get() {
        if (settings == null) load();

        return settings;
    }

    @Override
    public void reload() {
        load();
    }

    private void load() {
        settings = configLoader.load(dataPath.resolve("settings"), Settings.class);
    }
}