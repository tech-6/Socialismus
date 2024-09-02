package me.whereareiam.socialismus.command.listener;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import me.whereareiam.socialismus.api.input.serializer.SerializationService;
import me.whereareiam.socialismus.api.model.config.message.Messages;
import me.whereareiam.socialismus.api.model.player.DummyPlayer;
import me.whereareiam.socialismus.api.model.serializer.SerializerContent;
import me.whereareiam.socialismus.api.model.serializer.SerializerPlaceholder;
import me.whereareiam.socialismus.api.output.LoggingHelper;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.incendo.cloud.Command;
import org.incendo.cloud.processors.cooldown.CooldownInstance;
import org.incendo.cloud.processors.cooldown.listener.CooldownActiveListener;

import java.time.Duration;
import java.util.List;

@Singleton
public class CommandCooldownListener implements CooldownActiveListener<DummyPlayer> {
    private final SerializationService serializer;
    private final LoggingHelper loggingHelper;
    private final Provider<Messages> messages;

    @Inject
    public CommandCooldownListener(SerializationService serializer, LoggingHelper loggingHelper, Provider<Messages> messages) {
        this.serializer = serializer;
        this.loggingHelper = loggingHelper;
        this.messages = messages;
    }

    @Override
    public void cooldownActive(@NonNull DummyPlayer dummyPlayer, @NonNull Command<DummyPlayer> command, @NonNull CooldownInstance cooldown, @NonNull Duration remainingTime) {
        loggingHelper.debug("Cooldown active for " + dummyPlayer.getUsername() + " on command " + command.rootComponent().name() + " for " + remainingTime.getSeconds() + " seconds");

        dummyPlayer.sendMessage(serializer.format(new SerializerContent(
                dummyPlayer,
                List.of(new SerializerPlaceholder("{time}", String.valueOf(remainingTime.getSeconds()))),
                messages.get().getCommands().getCooldown()
        )));
    }
}
