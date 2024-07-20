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

    @Inject
    public ChatCoordinator(ChatMessageProcessor chatMessageProcessor, FormattedChatMessageProcessor formattedChatMessageProcessor) {
        this.chatMessageProcessor = chatMessageProcessor;
        this.formattedChatMessageProcessor = formattedChatMessageProcessor;
    }

    public FormattedChatMessage handleChatEvent(ChatMessage chatMessage) {
        chatMessage = chatMessageProcessor.process(chatMessage);

        return formattedChatMessageProcessor.process(chatMessage);
    }
}
