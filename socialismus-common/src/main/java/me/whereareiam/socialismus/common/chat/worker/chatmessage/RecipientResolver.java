package me.whereareiam.socialismus.common.chat.worker.chatmessage;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.whereareiam.socialismus.api.input.WorkerProcessor;
import me.whereareiam.socialismus.api.input.container.PlayerContainerService;
import me.whereareiam.socialismus.api.model.Worker;
import me.whereareiam.socialismus.api.model.chat.message.ChatMessage;

@Singleton
public class RecipientResolver {
    private final PlayerContainerService playerContainer;

    @Inject
    public RecipientResolver(WorkerProcessor<ChatMessage> workerProcessor, PlayerContainerService playerContainer) {
        this.playerContainer = playerContainer;
        workerProcessor.addWorker(new Worker<>(this::resolveRecipients, 0, true, false));
    }

    private ChatMessage resolveRecipients(ChatMessage chatMessage) {
        if (!chatMessage.getRecipients().isEmpty()) return chatMessage;

        chatMessage.setRecipients(playerContainer.getPlayers());

        return chatMessage;
    }
}
