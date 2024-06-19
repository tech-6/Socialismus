package me.whereareiam.socialismus.adapter.config.provider.chat;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import me.whereareiam.socialismus.adapter.config.ConfigLoader;
import me.whereareiam.socialismus.api.model.config.Messages;
import me.whereareiam.socialismus.api.model.config.chat.ChatMessages;

import java.nio.file.Path;

@Singleton
public class ChatMessagesProvider implements Provider<ChatMessages> {
	private final Path dataPath;

	@Inject
	public ChatMessagesProvider(@Named("chatPath") Path dataPath) {
		this.dataPath = dataPath;
	}

	@Override
	public ChatMessages get() {
		ConfigLoader<ChatMessages> configLoader = new ConfigLoader<>(dataPath);

		configLoader.load(ChatMessages.class, "", "messages.json");

		return configLoader.getConfig();
	}
}