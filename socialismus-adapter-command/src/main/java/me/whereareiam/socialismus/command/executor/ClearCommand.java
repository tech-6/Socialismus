package me.whereareiam.socialismus.command.executor;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import me.whereareiam.socialismus.api.input.chat.ChatHistoryService;
import me.whereareiam.socialismus.api.input.serializer.SerializationService;
import me.whereareiam.socialismus.api.model.CommandEntity;
import me.whereareiam.socialismus.api.model.chat.ChatSettings;
import me.whereareiam.socialismus.api.model.config.message.Messages;
import me.whereareiam.socialismus.api.model.player.DummyPlayer;
import me.whereareiam.socialismus.api.output.LoggingHelper;
import me.whereareiam.socialismus.api.output.PlatformInteractor;
import me.whereareiam.socialismus.api.output.command.CommandBase;
import org.incendo.cloud.annotations.Argument;
import org.incendo.cloud.annotations.Command;
import org.incendo.cloud.annotations.CommandDescription;
import org.incendo.cloud.annotations.Permission;

import java.util.Map;

@Singleton
public class ClearCommand implements CommandBase {
    private final LoggingHelper loggingHelper;
    private final SerializationService serializer;

    private final Provider<Messages> messages;
    private final Provider<ChatSettings> chatSettings;
    private final Provider<Map<String, CommandEntity>> commands;

    private final ChatHistoryService chatHistory;
    private final PlatformInteractor interactor;

    @Inject
    public ClearCommand(LoggingHelper loggingHelper, SerializationService serializer, Provider<Messages> messages, Provider<ChatSettings> chatSettings,
                        Provider<Map<String, CommandEntity>> commands, ChatHistoryService chatHistory, PlatformInteractor interactor) {
        this.loggingHelper = loggingHelper;
        this.serializer = serializer;
        this.messages = messages;
        this.chatSettings = chatSettings;
        this.commands = commands;
        this.chatHistory = chatHistory;
        this.interactor = interactor;
    }

    @Command("%command.clear")
    @CommandDescription("%description.clear")
    @Permission("%permission.clear")
    public void onCommand(DummyPlayer dummyPlayer, @Argument(value = "context") String context) {
        if (context == null) {
            handleNumericContext(dummyPlayer, chatSettings.get().getHistory().getHistorySize());
            return;
        }

        try {
            handleCommand(dummyPlayer, context);
        } catch (NumberFormatException e) {
            handleNonNumericContext(dummyPlayer, context);
        }
    }

    private void handleCommand(DummyPlayer dummyPlayer, String context) {
        try {
            int number = Integer.parseInt(context);
            if (number >= 1 && number <= chatSettings.get().getHistory().getHistorySize()) {
                handleNumericContext(dummyPlayer, number);
                return;
            }

            handleInvalidNumber(dummyPlayer, number);
        } catch (NumberFormatException e) {
            handleNonNumericContext(dummyPlayer, context);
        }
    }

    private void handleNumericContext(DummyPlayer dummyPlayer, int number) {
        int count = chatHistory.removeMessages(number);
        sendResponse(dummyPlayer, count, messages.get().getCommands().getClearCommand().getClearedAmount(), messages.get().getCommands().getClearCommand().getNoHistory());
    }

    private void handleInvalidNumber(DummyPlayer dummyPlayer, int number) {
        boolean removed = chatHistory.removeMessage(number);
        sendResponse(dummyPlayer, removed, messages.get().getCommands().getClearCommand().getCleared(), messages.get().getCommands().getClearCommand().getNoIdHistory().replace("{id}", String.valueOf(number)));
    }

    private void handleNonNumericContext(DummyPlayer dummyPlayer, String context) {
        if (interactor.hasPermission(context, chatSettings.get().getHistory().getBypassPermission())) {
            dummyPlayer.sendMessage(serializer.format(dummyPlayer, messages.get().getCommands().getClearCommand().getBypassUser()));
            return;
        }

        int count = chatHistory.removeMessages(context);
        sendResponse(dummyPlayer, count, messages.get().getCommands().getClearCommand().getClearedAmount(), messages.get().getCommands().getClearCommand().getNoUserHistory().replace("{playerName}", context));
    }

    private void sendResponse(DummyPlayer dummyPlayer, int count, String successMessage, String failureMessage) {
        if (count > 0) {
            loggingHelper.info("Deleted %s messages from chat history by %s", count, dummyPlayer.getUsername());
            dummyPlayer.sendMessage(serializer.format(dummyPlayer, successMessage.replace("{amount}", String.valueOf(count))));
        } else {
            dummyPlayer.sendMessage(serializer.format(dummyPlayer, failureMessage));
        }
    }

    private void sendResponse(DummyPlayer dummyPlayer, boolean removed, String successMessage, String failureMessage) {
        if (removed) {
            loggingHelper.info("Deleted message from chat history by %s", dummyPlayer.getUsername());
            dummyPlayer.sendMessage(serializer.format(dummyPlayer, successMessage));
        } else {
            dummyPlayer.sendMessage(serializer.format(dummyPlayer, failureMessage));
        }
    }

    @Override
    public Map<String, String> getTranslations() {
        CommandEntity commandEntity = commands.get().get("clear");

        return Map.of(
                "command." + commandEntity.getAliases().getFirst() + ".name", commandEntity.getUsage().replace("{alias}", String.join("|", commandEntity.getAliases())),
                "command." + commandEntity.getAliases().getFirst() + ".permission", commandEntity.getPermission(),
                "command." + commandEntity.getAliases().getFirst() + ".description", commandEntity.getDescription(),
                "command." + commandEntity.getAliases().getFirst() + ".usage", commandEntity.getUsage()
        );
    }
}