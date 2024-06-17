package me.whereareiam.socialismus.adapter.config;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import me.whereareiam.socialismus.adapter.config.provider.SettingsProvider;
import me.whereareiam.socialismus.api.model.Settings;

import java.nio.file.Path;

public class ConfigBinder extends AbstractModule {
	private final Path dataPath;

	public ConfigBinder(Path dataPath) {
		this.dataPath = dataPath;
	}

	@Override
	protected void configure() {
		bind(Path.class).annotatedWith(Names.named("dataPath")).toInstance(dataPath);

		bind(Settings.class).toProvider(SettingsProvider.class);
	}
}
