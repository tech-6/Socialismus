package me.whereareiam.socialismus.adapter.config.template.chat;

import com.google.inject.Singleton;
import me.whereareiam.socialismus.api.model.config.chat.ChatSettings;
import me.whereareiam.socialismus.api.output.DefaultConfig;

@Singleton
public class ChatSettingsTemplate implements DefaultConfig<ChatSettings> {
	@Override
	public ChatSettings getDefault() {
		ChatSettings chatSettings = new ChatSettings();

		// Default values

		return chatSettings;
	}
}
