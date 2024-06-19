package me.whereareiam.socialismus.adapter.config.provider;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import me.whereareiam.socialismus.adapter.config.ConfigLoader;
import me.whereareiam.socialismus.api.model.config.Messages;
import me.whereareiam.socialismus.api.model.config.Settings;

import java.nio.file.Path;

@Singleton
public class MessagesProvider implements Provider<Messages> {
	private final Path dataPath;

	@Inject
	public MessagesProvider(@Named("dataPath") Path dataPath) {
		this.dataPath = dataPath;
	}

	@Override
	public Messages get() {
		ConfigLoader<Messages> configLoader = new ConfigLoader<>(dataPath);

		configLoader.load(Messages.class, "", "messages.json");

		return configLoader.getConfig();
	}
}