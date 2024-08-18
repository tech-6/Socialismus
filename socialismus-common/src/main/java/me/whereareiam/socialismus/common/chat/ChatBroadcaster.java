package me.whereareiam.socialismus.common.chat;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import me.whereareiam.socialismus.api.ComponentUtil;
import me.whereareiam.socialismus.api.PlatformType;
import me.whereareiam.socialismus.api.input.container.PlayerContainerService;
import me.whereareiam.socialismus.api.input.serializer.SerializationService;
import me.whereareiam.socialismus.api.model.CommandEntity;
import me.whereareiam.socialismus.api.model.chat.ChatMessages;
import me.whereareiam.socialismus.api.model.chat.ChatSettings;
import me.whereareiam.socialismus.api.model.chat.message.FormattedChatMessage;
import me.whereareiam.socialismus.api.model.player.DummyPlayer;
import me.whereareiam.socialismus.api.output.LoggingHelper;
import me.whereareiam.socialismus.api.output.PlatformInteractor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.event.ClickEvent;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Singleton
public class ChatBroadcaster {
    private final LoggingHelper loggingHelper;
    private final PlayerContainerService playerContainer;
    private final PlatformInteractor interactor;

    private final Provider<ChatSettings> chatSettings;
    private final Provider<ChatMessages> chatMessages;
    private final Provider<Map<String, CommandEntity>> commands;

    private final SerializationService serializer;

    @Inject
    public ChatBroadcaster(LoggingHelper loggingHelper, PlayerContainerService playerContainer, PlatformInteractor interactor, Provider<ChatSettings> chatSettings, Provider<ChatMessages> chatMessages, Provider<Map<String, CommandEntity>> commands, SerializationService serializer) {
        this.loggingHelper = loggingHelper;
        this.playerContainer = playerContainer;
        this.interactor = interactor;
        this.chatSettings = chatSettings;
        this.chatMessages = chatMessages;
        this.commands = commands;
        this.serializer = serializer;
    }

    public void broadcast(FormattedChatMessage chatMessage, boolean simple) {
        loggingHelper.info("[%s] %s: %s", chatMessage.getChat().getId().toUpperCase(), chatMessage.getSender().getUsername(), ComponentUtil.toString(chatMessage.getContent(), true));

        if (simple || PlatformType.isProxy() || !chatMessage.isVanillaSending())
            chatMessage.getRecipients().forEach(recipient ->
                    recipient.sendMessage(
                            chatMessage.getFormat()
                                    .replaceText(createMessageReplacement(chatMessage.getContent()))
                                    .replaceText(createClearReplacement(chatMessage, recipient.getUniqueId()))
                    )
            );
    }

    public void broadcast(FormattedChatMessage chatMessage) {
        broadcast(chatMessage, false);
    }

    public TextReplacementConfig createMessageReplacement(Component component) {
        return TextReplacementConfig.builder()
                .matchLiteral("{message}")
                .replacement(component)
                .build();
    }

    public TextReplacementConfig createClearReplacement(FormattedChatMessage formattedChatMessage, UUID recipientUniqueId) {
        DummyPlayer sender = formattedChatMessage.getSender();
        Optional<DummyPlayer> recipient = playerContainer.getPlayer(recipientUniqueId);

        if (recipient.isPresent()
                && interactor.hasPermission(recipient.get(), chatSettings.get().getHistory().getPermission())
                && !interactor.hasPermission(sender, chatSettings.get().getHistory().getBypassPermission())) {
            return TextReplacementConfig.builder()
                    .matchLiteral("{clear}")
                    .replacement(serializer.format(sender, chatMessages.get().getClearFormat().getFormat())
                            .clickEvent(ClickEvent.runCommand(
                                    "/" + commands.get().get("clear").getUsage()
                                            .replace("{command}", commands.get().get("main").getAliases().getFirst())
                                            .replace("{alias}", commands.get().get("clear").getAliases().getFirst())
                                            .replace("[context]", String.valueOf(formattedChatMessage.getId()))))
                    )
                    .build();
        }

        return TextReplacementConfig.builder()
                .matchLiteral("{clear}")
                .replacement(Component.empty())
                .build();
    }
}
