package me.whereareiam.socialismus.adapter.config.provider.chat;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import me.whereareiam.socialismus.adapter.config.ConfigLoader;
import me.whereareiam.socialismus.api.model.config.chat.ChatSettings;

import java.nio.file.Path;

@Singleton
public class ChatSettingsProvider implements Provider<ChatSettings> {
	private final Path dataPath;

	@Inject
	public ChatSettingsProvider(@Named("chatPath") Path dataPath) {
		this.dataPath = dataPath;
	}

	@Override
	public ChatSettings get() {
		ConfigLoader<ChatSettings> configLoader = new ConfigLoader<>(dataPath);

		configLoader.load(ChatSettings.class, "", "settings.json");

		return configLoader.getConfig();
	}
}