package me.whereareiam.socialismus.common.chat.worker.chatmessage;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.whereareiam.socialismus.api.input.WorkerProcessor;
import me.whereareiam.socialismus.api.model.Worker;
import me.whereareiam.socialismus.api.model.chat.Chat;
import me.whereareiam.socialismus.api.model.chat.message.ChatMessage;
import me.whereareiam.socialismus.api.model.player.DummyPlayer;
import me.whereareiam.socialismus.api.model.requirement.Requirement;
import me.whereareiam.socialismus.api.output.PlatformInteractor;
import me.whereareiam.socialismus.api.type.Participants;
import me.whereareiam.socialismus.api.type.requirement.RequirementType;
import me.whereareiam.socialismus.common.requirement.RequirementValidator;

import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Singleton
public class RecipientSelector {
    private final RequirementValidator requirementValidator;
    private final PlatformInteractor interactor;

    @Inject
    public RecipientSelector(WorkerProcessor<ChatMessage> workerProcessor, RequirementValidator requirementValidator, PlatformInteractor interactor) {
        this.requirementValidator = requirementValidator;
        this.interactor = interactor;

        workerProcessor.addWorker(new Worker<>(this::selectRecipients, 50, true, false));
    }

    private ChatMessage selectRecipients(ChatMessage chatMessage) {
        if (chatMessage.getChat() == null) {
            chatMessage.setCancelled(true);
            return chatMessage;
        }

        Chat chat = chatMessage.getChat();
        DummyPlayer sender = chatMessage.getSender();
        Set<UUID> recipients = chatMessage.getRecipients();

        if (chat.getParameters().getType().isLocal()) {
            recipients = recipients.parallelStream()
                    .filter(recipient -> isInSameRealm(sender, recipient))
                    .filter(recipient -> isWithinRadius(sender, recipient, chat.getParameters().getRadius()))
                    .filter(recipient -> checkRequirements(chat, recipient))
                    .collect(Collectors.toSet());
        } else {
            recipients = recipients.stream()
                    .filter(recipient -> checkRequirements(chat, recipient))
                    .collect(Collectors.toSet());
        }

        chatMessage.setRecipients(recipients);
        if (recipients.isEmpty()) chatMessage.setCancelled(true);

        return chatMessage;
    }

    private boolean checkRequirements(Chat chat, UUID uniqueId) {
        if (chat.getRequirements().isEmpty()) return true;
        for (Map.Entry<RequirementType, ? extends Requirement> entry : chat.getRequirements().get(Participants.RECIPIENT).getGroups().entrySet())
            return interactor.getDummyPlayer(uniqueId).map(player -> requirementValidator.isRequirementMet(entry, player)).orElse(false);

        return true;
    }

    private boolean isInSameRealm(DummyPlayer sender, UUID recipient) {
        return interactor.getDummyPlayer(recipient).map(r -> r.getLocation().equals(sender.getLocation())).orElse(false);
    }

    private boolean isWithinRadius(DummyPlayer sender, UUID recipient, double radius) {
        return interactor.areWithinRange(sender.getUniqueId(), recipient, radius);
    }
}