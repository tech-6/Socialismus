package me.whereareiam.socialismus.integration.valiobungee;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.imaginarycode.minecraft.redisbungee.events.PubSubMessageEvent;
import me.whereareiam.socialismus.api.model.chat.message.ChatMessage;
import me.whereareiam.socialismus.api.output.LoggingHelper;
import me.whereareiam.socialismus.api.output.listener.DynamicListener;
import me.whereareiam.socialismus.common.Constants;
import me.whereareiam.socialismus.common.chat.ChatCoordinator;

import java.io.IOException;

@Singleton
public class PubSubMessageListener implements DynamicListener<PubSubMessageEvent> {
    private final ChatCoordinator chatCoordinator;
    private final LoggingHelper loggingHelper;

    @Inject
    public PubSubMessageListener(ChatCoordinator chatCoordinator, LoggingHelper loggingHelper) {
        this.chatCoordinator = chatCoordinator;
        this.loggingHelper = loggingHelper;
    }

    public void onEvent(PubSubMessageEvent event) {
        if (!event.getChannel().equals(Constants.getChannel())) return;

        try {
            ChatMessage chatMessage = ChatMessage.deserialize(event.getMessage());

            chatCoordinator.handleChatEvent(chatMessage);
        } catch (IOException | ClassNotFoundException e) {
            loggingHelper.severe("Failed to deserialize chat message %s", e);
        }
    }
}
