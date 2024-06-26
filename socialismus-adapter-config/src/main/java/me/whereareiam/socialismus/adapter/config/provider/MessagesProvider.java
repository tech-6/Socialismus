package me.whereareiam.socialismus.adapter.config.provider;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import me.whereareiam.socialismus.adapter.config.ConfigLoader;
import me.whereareiam.socialismus.api.model.config.Messages;

import java.nio.file.Path;

@Singleton
public class MessagesProvider implements Provider<Messages> {
	private final Path dataPath;
	private final ConfigLoader configLoader;

	@Inject
	public MessagesProvider(@Named("dataPath") Path dataPath, ConfigLoader configLoader) {
		this.dataPath = dataPath;
		this.configLoader = configLoader;
	}

	@Override
	public Messages get() {
		return configLoader.load(dataPath.resolve("messages"), Messages.class);
	}
}