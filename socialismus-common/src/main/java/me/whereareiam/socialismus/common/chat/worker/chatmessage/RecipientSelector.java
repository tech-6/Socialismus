package me.whereareiam.socialismus.common.chat.worker.chatmessage;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import me.whereareiam.socialismus.api.EventUtil;
import me.whereareiam.socialismus.api.input.WorkerProcessor;
import me.whereareiam.socialismus.api.input.event.chat.recipient.RecipientsSelectedEvent;
import me.whereareiam.socialismus.api.model.Worker;
import me.whereareiam.socialismus.api.model.chat.Chat;
import me.whereareiam.socialismus.api.model.chat.ChatMessages;
import me.whereareiam.socialismus.api.model.chat.ChatSettings;
import me.whereareiam.socialismus.api.model.chat.message.ChatMessage;
import me.whereareiam.socialismus.api.model.player.DummyPlayer;
import me.whereareiam.socialismus.api.output.LoggingHelper;
import me.whereareiam.socialismus.api.output.PlatformInteractor;
import me.whereareiam.socialismus.api.type.Participants;
import me.whereareiam.socialismus.common.requirement.RequirementEvaluator;
import me.whereareiam.socialismus.common.serializer.Serializer;

import java.util.Set;
import java.util.stream.Collectors;

@Singleton
public class RecipientSelector {
    private final RequirementEvaluator requirementEvaluator;
    private final Provider<ChatSettings> settings;
    private final Provider<ChatMessages> messages;
    private final PlatformInteractor interactor;
    private final LoggingHelper loggingHelper;
    private final Serializer serializer;

    @Inject
    public RecipientSelector(WorkerProcessor<ChatMessage> workerProcessor, RequirementEvaluator requirementEvaluator, Provider<ChatSettings> settings, Provider<ChatMessages> messages,
                             PlatformInteractor interactor, LoggingHelper loggingHelper, Serializer serializer) {
        this.requirementEvaluator = requirementEvaluator;
        this.settings = settings;
        this.messages = messages;
        this.interactor = interactor;
        this.loggingHelper = loggingHelper;
        this.serializer = serializer;

        workerProcessor.addWorker(new Worker<>(this::selectRecipients, 100, true, false));
    }

    private ChatMessage selectRecipients(ChatMessage chatMessage) {
        if (chatMessage.getChat() == null) {
            chatMessage.setCancelled(true);
            return chatMessage;
        }

        Chat chat = chatMessage.getChat();
        DummyPlayer sender = chatMessage.getSender();

        int oldRecipients = chatMessage.getRecipients().size();
        Set<DummyPlayer> recipients = chatMessage.getRecipients();

        if (chat.getParameters().getType().isLocal()) {
            recipients = recipients.parallelStream()
                    .filter(recipient -> isInSameRealm(sender, recipient))
                    .filter(recipient -> isWithinRadius(sender, recipient, chat.getParameters().getRadius()))
                    .filter(recipient -> checkRequirements(chat, recipient))
                    .collect(Collectors.toSet());

            if (recipients.isEmpty() && settings.get().isNotifyNoNearbyPlayers())
                sender.sendMessage(serializer.format(sender, messages.get().getNoNearbyPlayers()));
        } else {
            recipients = recipients.stream()
                    .filter(recipient -> checkRequirements(chat, recipient))
                    .collect(Collectors.toSet());
        }

        RecipientsSelectedEvent event = new RecipientsSelectedEvent(chatMessage, recipients, chatMessage.isCancelled());
        EventUtil.callEvent(event, () -> chatMessage.setRecipients(event.getNewRecipients()));
        loggingHelper.debug("Recipients before: " + oldRecipients + ", after: " + event.getNewRecipients().size());

        if (recipients.isEmpty()) chatMessage.setCancelled(true);
        if (recipients.isEmpty() && settings.get().isNotifyNoPlayers() && chat.getParameters().getType().isGlobal())
            sender.sendMessage(serializer.format(sender, messages.get().getNoPlayers()));

        return chatMessage;
    }

    private boolean checkRequirements(Chat chat, DummyPlayer dummyPlayer) {
        return requirementEvaluator.check(chat.getRequirements().get(Participants.RECIPIENT), dummyPlayer);
    }

    private boolean isInSameRealm(DummyPlayer sender, DummyPlayer recipient) {
        return recipient.getLocation().equals(sender.getLocation());
    }

    private boolean isWithinRadius(DummyPlayer sender, DummyPlayer recipient, double radius) {
        return interactor.areWithinRange(sender.getUniqueId(), recipient.getUniqueId(), radius);
    }
}