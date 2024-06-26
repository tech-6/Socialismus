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
	private final ConfigLoader configLoader;

	@Inject
	public ChatSettingsProvider(@Named("chatPath") Path dataPath, ConfigLoader configLoader) {
		this.dataPath = dataPath;
		this.configLoader = configLoader;
	}

	@Override
	public ChatSettings get() {
		return configLoader.load(dataPath.resolve("settings"), ChatSettings.class);
	}
}