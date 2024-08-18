package me.whereareiam.socialismus.common.chat;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.whereareiam.socialismus.api.input.container.ChatHistoryContainerService;
import me.whereareiam.socialismus.api.model.chat.message.ChatMessage;
import me.whereareiam.socialismus.api.model.chat.message.FormattedChatMessage;
import me.whereareiam.socialismus.common.chat.processor.ChatMessageProcessor;
import me.whereareiam.socialismus.common.chat.processor.FormattedChatMessageProcessor;

@Singleton
public class ChatCoordinator {
    private final ChatMessageProcessor chatMessageProcessor;
    private final FormattedChatMessageProcessor formattedChatMessageProcessor;
    private final ChatBroadcaster chatBroadcaster;
    private final ChatHistoryContainerService chatHistoryContainer;

    @Inject
    public ChatCoordinator(ChatMessageProcessor chatMessageProcessor, FormattedChatMessageProcessor formattedChatMessageProcessor,
                           ChatBroadcaster chatBroadcaster, ChatHistoryContainerService chatHistoryContainer) {
        this.chatMessageProcessor = chatMessageProcessor;
        this.formattedChatMessageProcessor = formattedChatMessageProcessor;
        this.chatBroadcaster = chatBroadcaster;
        this.chatHistoryContainer = chatHistoryContainer;
    }

    public FormattedChatMessage handleChatEvent(ChatMessage chatMessage) {
        chatMessage = chatMessageProcessor.process(chatMessage);
        if (chatMessage.isCancelled()) return FormattedChatMessage.builder().cancelled(true).build();

        FormattedChatMessage formattedChatMessage = formattedChatMessageProcessor.process(chatMessage);
        if (formattedChatMessage.isCancelled()) return formattedChatMessage;

        chatBroadcaster.broadcast(formattedChatMessage);
        chatHistoryContainer.addMessage(formattedChatMessage.getId(), formattedChatMessage);

        return formattedChatMessage;
    }
}
