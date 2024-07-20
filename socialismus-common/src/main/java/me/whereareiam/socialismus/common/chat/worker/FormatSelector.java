package me.whereareiam.socialismus.common.chat.worker;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import me.whereareiam.socialismus.api.input.WorkerProcessor;
import me.whereareiam.socialismus.api.model.Worker;
import me.whereareiam.socialismus.api.model.chat.ChatFormat;
import me.whereareiam.socialismus.api.model.chat.ChatMessages;
import me.whereareiam.socialismus.api.model.chat.ChatSettings;
import me.whereareiam.socialismus.api.model.chat.message.FormattedChatMessage;
import me.whereareiam.socialismus.api.model.requirement.Requirement;
import me.whereareiam.socialismus.api.model.serializer.SerializerContent;
import me.whereareiam.socialismus.api.model.serializer.SerializerPlaceholder;
import me.whereareiam.socialismus.api.output.LoggingHelper;
import me.whereareiam.socialismus.api.type.Participants;
import me.whereareiam.socialismus.api.type.requirement.RequirementType;
import me.whereareiam.socialismus.common.requirement.RequirementValidator;
import me.whereareiam.socialismus.common.serializer.Serializer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Singleton
public class FormatSelector {
    private final LoggingHelper loggingHelper;
    private final RequirementValidator requirementValidator;

    // Configs
    private final Provider<ChatMessages> chatMessages;
    private final Provider<ChatSettings> chatSettings;

    // Communication
    private final Serializer serializer;

    @Inject
    public FormatSelector(LoggingHelper loggingHelper, WorkerProcessor<FormattedChatMessage> workerProcessor, RequirementValidator requirementValidator,
                          Provider<ChatMessages> chatMessages, Provider<ChatSettings> chatSettings, Serializer serializer) {
        this.loggingHelper = loggingHelper;
        this.requirementValidator = requirementValidator;
        this.chatMessages = chatMessages;
        this.chatSettings = chatSettings;
        this.serializer = serializer;

        // init configs
        chatMessages.get();

        workerProcessor.addWorker(new Worker<>(this::formatChat, 1, true, false));
    }

    private FormattedChatMessage formatChat(FormattedChatMessage formattedChatMessage) {
        loggingHelper.debug("Formatting chat message for user " + formattedChatMessage.getSender().getUsername());

        ChatFormat chatFormat = formattedChatMessage.getChat().getFormats().getLast();
        if (chatFormat == null || !checkRequirements(chatFormat, formattedChatMessage)) {
            notifyAboutAbsentFormat(formattedChatMessage);
            formattedChatMessage.setCancelled(true);

            return formattedChatMessage;
        }

        loggingHelper.debug("Selected format: " + chatFormat);
        formattedChatMessage.setFormat(serializer.format(new SerializerContent(
                formattedChatMessage.getSender(),
                List.of(
                        new SerializerPlaceholder("{playerName}", formattedChatMessage.getSender().getUsername())
                ),
                chatFormat.getFormat()
        )));

        return formattedChatMessage;
    }

    private boolean checkRequirements(ChatFormat chatFormat, FormattedChatMessage formattedChatMessage) {
        if (chatFormat.getRequirements().isEmpty()) return true;
        for (Map.Entry<RequirementType, ? extends Requirement> entry : chatFormat.getRequirements().get(Participants.SENDER).getGroups().entrySet())
            if (!requirementValidator.isRequirementMet(entry, formattedChatMessage)) {
                chatFormat = getAlternativeChatFormat(chatFormat, formattedChatMessage);
                if (chatFormat == null) return false;
            }

        return true;
    }

    private ChatFormat getAlternativeChatFormat(ChatFormat chatFormat, FormattedChatMessage formattedChatMessage) {
        List<ChatFormat> formats = new ArrayList<>(formattedChatMessage.getChat().getFormats());
        Collections.reverse(formats);

        formats.remove(chatFormat);
        for (ChatFormat alternativeChat : formats)
            if (checkRequirements(alternativeChat, formattedChatMessage))
                return alternativeChat;

        return null;
    }

    private void notifyAboutAbsentFormat(FormattedChatMessage formattedChatMessage) {
        if (!chatSettings.get().isNotifyNoFormat()) return;
        
        formattedChatMessage.getSender().getAudience().sendMessage(
                serializer.format(formattedChatMessage.getSender(), chatMessages.get().getNoChatMatch())
        );
    }
}
