package me.whereareiam.socialismus.adapter.config.provider;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.name.Named;
import me.whereareiam.socialismus.adapter.config.management.ConfigLoader;
import me.whereareiam.socialismus.api.model.config.settings.Settings;

import java.nio.file.Path;

public class SettingsProvider implements Provider<Settings> {
	private final Path dataPath;
	private final ConfigLoader configLoader;

	@Inject
	public SettingsProvider(@Named("dataPath") Path dataPath, ConfigLoader configLoader) {
		this.dataPath = dataPath;
		this.configLoader = configLoader;
	}

	@Override
	public Settings get() {
		return configLoader.load(dataPath.resolve("settings"), Settings.class);
	}
}