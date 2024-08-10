package me.whereareiam.socialismus.command.executor;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import me.whereareiam.socialismus.api.Reloadable;
import me.whereareiam.socialismus.api.input.serializer.SerializationService;
import me.whereareiam.socialismus.api.model.config.command.Commands;
import me.whereareiam.socialismus.api.model.config.message.CommandMessages;
import me.whereareiam.socialismus.api.model.config.message.Messages;
import me.whereareiam.socialismus.api.model.player.DummyPlayer;
import me.whereareiam.socialismus.api.output.command.CommandBase;
import net.kyori.adventure.audience.Audience;
import org.incendo.cloud.annotations.Command;
import org.incendo.cloud.annotations.CommandDescription;
import org.incendo.cloud.annotations.Permission;

import java.util.Map;
import java.util.Set;

@Singleton
@Command("%command.main")
public class ReloadCommand implements CommandBase {
    private final Set<Reloadable> reloadables;
    private final Provider<Messages> messages;
    private final Provider<Commands> commands;
    private final SerializationService serializer;

    @Inject
    public ReloadCommand(@Named("reloadables") Set<Reloadable> reloadables, Provider<Messages> messages, Provider<Commands> commands, SerializationService serializer) {
        this.reloadables = reloadables;
        this.messages = messages;
        this.commands = commands;
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

    @Override
    public Map<String, String> getTranslations() {
        final me.whereareiam.socialismus.api.model.config.command.Command command = commands.get().getCommands().get("reload");

        return Map.of(
                "command." + command.getAliases().getFirst() + ".name", command.getUsage().replace("{command}", String.join("|", command.getAliases())),
                "command." + command.getAliases().getFirst() + ".permission", command.getPermission(),
                "command." + command.getAliases().getFirst() + ".description", command.getDescription(),
                "command." + command.getAliases().getFirst() + ".usage", command.getUsage()
        );
    }
}
