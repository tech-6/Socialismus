package me.whereareiam.socialismus.common.chat;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.Getter;
import me.whereareiam.socialismus.api.model.chat.message.ChatMessage;
import me.whereareiam.socialismus.api.model.chat.message.FormattedChatMessage;
import me.whereareiam.socialismus.common.chat.processor.ChatMessageProcessor;
import me.whereareiam.socialismus.common.chat.processor.FormattedChatMessageProcessor;

@Getter
@Singleton
public class ChatCoordinator {
    private final ChatMessageProcessor chatMessageProcessor;
    private final FormattedChatMessageProcessor formattedChatMessageProcessor;
    private final ChatBroadcaster chatBroadcaster;

    @Inject
    public ChatCoordinator(ChatMessageProcessor chatMessageProcessor, FormattedChatMessageProcessor formattedChatMessageProcessor, ChatBroadcaster chatBroadcaster) {
        this.chatMessageProcessor = chatMessageProcessor;
        this.formattedChatMessageProcessor = formattedChatMessageProcessor;
        this.chatBroadcaster = chatBroadcaster;
    }

    public FormattedChatMessage handleChatEvent(ChatMessage chatMessage) {
        chatMessage = chatMessageProcessor.process(chatMessage);

        FormattedChatMessage formattedChatMessage = formattedChatMessageProcessor.process(chatMessage);

        if (!formattedChatMessage.isVanillaSending()) {
            formattedChatMessage.setCancelled(true);
            chatBroadcaster.broadcast(formattedChatMessage);
        }

        return formattedChatMessage;
    }
}
