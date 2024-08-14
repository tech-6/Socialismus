package me.whereareiam.socialismus.common.chat;

import com.google.inject.Singleton;
import me.whereareiam.socialismus.api.model.chat.message.FormattedChatMessage;
import me.whereareiam.socialismus.api.model.player.DummyPlayer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;

import java.util.Set;

@Singleton
public class ChatBroadcaster {
    public void broadcast(FormattedChatMessage chatMessage) {
        Set<DummyPlayer> recipients = chatMessage.getRecipients();

        recipients.forEach(recipient ->
                recipient.sendMessage(
                        chatMessage.getFormat().replaceText(createTextReplacement(chatMessage.getContent()))
                )
        );
    }

    private TextReplacementConfig createTextReplacement(Component component) {
        return TextReplacementConfig.builder()
                .matchLiteral("{message}")
                .replacement(component)
                .build();
    }
}
