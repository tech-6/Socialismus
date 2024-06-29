package me.whereareiam.socialismus.adapter.config.provider;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import me.whereareiam.socialismus.adapter.config.management.ConfigLoader;
import me.whereareiam.socialismus.api.model.config.command.Commands;

import java.nio.file.Path;

@Singleton
public class CommandsProvider implements Provider<Commands> {
	private final Path dataPath;
	private final ConfigLoader configLoader;

	@Inject
	public CommandsProvider(@Named("dataPath") Path dataPath, ConfigLoader configLoader) {
		this.dataPath = dataPath;
		this.configLoader = configLoader;
	}

	@Override
	public Commands get() {
		return configLoader.load(dataPath.resolve("commands"), Commands.class);
	}
}