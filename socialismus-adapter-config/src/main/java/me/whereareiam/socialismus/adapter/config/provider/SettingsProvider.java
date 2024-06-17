package me.whereareiam.socialismus.adapter.config.provider;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import me.whereareiam.socialismus.adapter.config.ConfigLoader;
import me.whereareiam.socialismus.api.model.Settings;

import java.nio.file.Path;

@Singleton
public class SettingsProvider implements Provider<Settings> {
	private final Path dataPath;

	@Inject
	public SettingsProvider(@Named("dataPath") Path dataPath) {
		this.dataPath = dataPath;
	}

	@Override
	public Settings get() {
		ConfigLoader<Settings> configLoader = new ConfigLoader<>(dataPath);

		configLoader.load(Settings.class, "", "settings.json");

		return configLoader.getConfig();
	}
}