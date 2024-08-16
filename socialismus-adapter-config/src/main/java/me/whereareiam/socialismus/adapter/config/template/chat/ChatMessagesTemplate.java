package me.whereareiam.socialismus.adapter.config.template.chat;

import com.google.inject.Singleton;
import me.whereareiam.socialismus.api.model.chat.ChatMessages;
import me.whereareiam.socialismus.api.output.DefaultConfig;

@Singleton
public class ChatMessagesTemplate implements DefaultConfig<ChatMessages> {
    @Override
    public ChatMessages getDefault() {
        ChatMessages chatMessages = new ChatMessages();

        // Default values
        chatMessages.setNoPlayers("{prefix}<white>Your message was not sent because there are no players online.");
        chatMessages.setNoChatMatch("{prefix}<white>No chat matching criteria found.");
        chatMessages.setNoFormatMatch("{prefix}<white>No format matching criteria found.");
        chatMessages.setNoFallbackChat("{prefix}<white>No fallback chat found.");
        chatMessages.setNoNearbyPlayers("{prefix}<white>No nearby players found, within a radius of <gray>{radius}</gray> blocks.");

        ChatMessages.ClearFormat clearFormat = new ChatMessages.ClearFormat();
        clearFormat.setFormat("<gray>[<red>X</red>]</gray> ");

        chatMessages.setClearFormat(clearFormat);

        return chatMessages;
    }
}
