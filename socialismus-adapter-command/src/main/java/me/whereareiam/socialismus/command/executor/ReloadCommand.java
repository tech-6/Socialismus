package me.whereareiam.socialismus.command.executor;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import me.whereareiam.socialismus.api.EventUtil;
import me.whereareiam.socialismus.api.Reloadable;
import me.whereareiam.socialismus.api.input.event.plugin.PluginReloadedEvent;
import me.whereareiam.socialismus.api.input.serializer.SerializationService;
import me.whereareiam.socialismus.api.model.CommandEntity;
import me.whereareiam.socialismus.api.model.config.message.CommandMessages;
import me.whereareiam.socialismus.api.model.config.message.Messages;
import me.whereareiam.socialismus.api.model.player.DummyPlayer;
import me.whereareiam.socialismus.api.output.command.CommandBase;
import me.whereareiam.socialismus.api.output.command.CommandCooldown;
import net.kyori.adventure.audience.Audience;
import org.incendo.cloud.annotations.Command;
import org.incendo.cloud.annotations.CommandDescription;
import org.incendo.cloud.annotations.Permission;

import java.util.Map;
import java.util.Set;

@Singleton
public class ReloadCommand extends CommandBase {
    private static final String COMMAND_NAME = "reload";
    private final Provider<Map<String, CommandEntity>> commands;

    private final Set<Reloadable> reloadables;
    private final Provider<Messages> messages;
    private final SerializationService serializer;

    @Inject
    public ReloadCommand(Provider<Map<String, CommandEntity>> commands, @Named("reloadables") Set<Reloadable> reloadables,
                         Provider<Messages> messages, SerializationService serializer) {
        super(COMMAND_NAME);
        this.commands = commands;

        this.reloadables = reloadables;
        this.messages = messages;
        this.serializer = serializer;
    }

    @Command("%command." + COMMAND_NAME)
    @CommandDescription("%description." + COMMAND_NAME)
    @CommandCooldown("%cooldown." + COMMAND_NAME)
    @Permission("%permission." + COMMAND_NAME)
    public void onCommand(DummyPlayer dummyPlayer) {
        Audience audience = dummyPlayer.getAudience();
        Messages messages = this.messages.get();
        CommandMessages.ReloadCommand commandMessages = messages.getCommands().getReloadCommand();

        audience.sendMessage(serializer.format(dummyPlayer, commandMessages.getReloading()));

        try {
            if (EventUtil.callEvent(new PluginReloadedEvent(false), () -> reloadables.forEach(Reloadable::reload)))
                audience.sendMessage(serializer.format(dummyPlayer, commandMessages.getReloaded()));
            else audience.sendMessage(serializer.format(dummyPlayer, messages.getCommands().getCancelled()));
        } catch (Exception e) {
            audience.sendMessage(serializer.format(dummyPlayer, commandMessages.getException().replace("{exception}", e.getMessage())));
        }
    }

    @Override
    public CommandEntity getCommandEntity() {
        return commands.get().get(COMMAND_NAME);
    }
}
