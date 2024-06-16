package me.whereareiam.socialismus.adapter.config;

import com.google.inject.AbstractModule;

import java.nio.file.Path;

public class ConfigInitializer extends AbstractModule {
	private final Path dataPath;

	public ConfigInitializer(Path dataPath) {
		this.dataPath = dataPath;
	}

	@Override
	protected void configure() {

	}
}
