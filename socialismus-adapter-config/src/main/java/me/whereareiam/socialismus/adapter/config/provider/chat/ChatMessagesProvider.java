package me.whereareiam.socialismus.adapter.config.provider.chat;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import me.whereareiam.socialismus.adapter.config.management.ConfigLoader;
import me.whereareiam.socialismus.api.model.config.chat.ChatMessages;

import java.nio.file.Path;

@Singleton
public class ChatMessagesProvider implements Provider<ChatMessages> {
	private final Path dataPath;
	private final ConfigLoader configLoader;

	@Inject
	public ChatMessagesProvider(@Named("chatPath") Path dataPath, ConfigLoader configLoader) {
		this.dataPath = dataPath;
		this.configLoader = configLoader;
	}

	@Override
	public ChatMessages get() {
		return configLoader.load(dataPath.resolve("messages"), ChatMessages.class);
	}
}