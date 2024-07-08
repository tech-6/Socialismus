package me.whereareiam.socialismus.command.executor;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import me.whereareiam.socialismus.api.Reloadable;
import me.whereareiam.socialismus.api.input.serializer.SerializationService;
import me.whereareiam.socialismus.api.model.config.message.CommandMessages;
import me.whereareiam.socialismus.api.model.config.message.Messages;
import me.whereareiam.socialismus.api.model.player.DummyPlayer;
import me.whereareiam.socialismus.api.output.command.CommandBase;
import net.kyori.adventure.audience.Audience;
import org.incendo.cloud.annotations.Command;
import org.incendo.cloud.annotations.CommandDescription;
import org.incendo.cloud.annotations.Permission;

import java.util.Set;

@Singleton
@Command("%command.main")
public class ReloadCommand implements CommandBase {
    private final Set<Reloadable> reloadables;
    private final Provider<Messages> messages;
    private final SerializationService serializer;

    @Inject
    public ReloadCommand(@Named("reloadables") Set<Reloadable> reloadables, Provider<Messages> messages, SerializationService serializer) {
        this.reloadables = reloadables;
        this.messages = messages;
        this.serializer = serializer;
    }

    @Command("%command.reload")
    @CommandDescription("%description.reload")
    @Permission("%permission.reload")
    public void onCommand(DummyPlayer dummyPlayer) {
        Audience audience = dummyPlayer.getAudience();
        CommandMessages.ReloadCommand commandMessages = messages.get().getCommands().getReloadCommand();
        audience.sendMessage(serializer.format(dummyPlayer, commandMessages.getReloading()));

        try {
            reloadables.forEach(Reloadable::reload);
            audience.sendMessage(serializer.format(dummyPlayer, commandMessages.getReloaded()));
        } catch (Exception e) {
            audience.sendMessage(serializer.format(dummyPlayer, commandMessages.getException().replace("{exception}", e.getMessage())));
        }
    }
}
