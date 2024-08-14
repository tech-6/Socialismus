package me.whereareiam.socialismus.common.chat;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.whereareiam.socialismus.api.ComponentUtil;
import me.whereareiam.socialismus.api.PlatformType;
import me.whereareiam.socialismus.api.model.chat.message.FormattedChatMessage;
import me.whereareiam.socialismus.api.model.player.DummyPlayer;
import me.whereareiam.socialismus.api.output.LoggingHelper;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;

import java.util.Set;

@Singleton
public class ChatBroadcaster {
    private final LoggingHelper loggingHelper;

    @Inject
    public ChatBroadcaster(LoggingHelper loggingHelper) {
        this.loggingHelper = loggingHelper;
    }

    public void broadcast(FormattedChatMessage chatMessage) {
        loggingHelper.info("[%s] %s: %s", chatMessage.getChat().getId().toUpperCase(), chatMessage.getSender().getUsername(), ComponentUtil.toString(chatMessage.getContent(), true));

        if (!PlatformType.isProxy() || chatMessage.isVanillaSending()) return;
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
