package me.whereareiam.socialismus.adapter.config.template.chat;

import com.google.inject.Singleton;
import me.whereareiam.socialismus.api.model.chat.ChatSettings;
import me.whereareiam.socialismus.api.output.DefaultConfig;

@Singleton
public class ChatSettingsTemplate implements DefaultConfig<ChatSettings> {
    @Override
    public ChatSettings getDefault() {
        ChatSettings chatSettings = new ChatSettings();

        // Default values
        chatSettings.setNotifyNoChat(true);
        chatSettings.setNotifyNoFormat(true);
        chatSettings.setNotifyNoPlayers(false);
        chatSettings.setNotifyNoNearbyPlayers(false);

        ChatSettings.FallbackChatSettings fallback = new ChatSettings.FallbackChatSettings();
        fallback.setEnabled(true);
        fallback.setChatId("fallback");

        chatSettings.setFallback(fallback);

        return chatSettings;
    }
}
