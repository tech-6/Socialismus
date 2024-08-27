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
import net.kyori.adventure.audience.Audience;
import org.incendo.cloud.annotations.Command;
import org.incendo.cloud.annotations.CommandDescription;
import org.incendo.cloud.annotations.Permission;

import java.util.Map;
import java.util.Set;

@Singleton
public class ReloadCommand implements CommandBase {
    private final Set<Reloadable> reloadables;
    private final Provider<Messages> messages;
    private final Provider<Map<String, CommandEntity>> commands;
    private final SerializationService serializer;

    @Inject
    public ReloadCommand(@Named("reloadables") Set<Reloadable> reloadables, Provider<Messages> messages, Provider<Map<String, CommandEntity>> commands, SerializationService serializer) {
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
    public Map<String, String> getTranslations() {
        CommandEntity commandEntity = commands.get().get("reload");

        return Map.of(
                "command." + commandEntity.getAliases().getFirst() + ".name", commandEntity.getUsage().replace("{alias}", String.join("|", commandEntity.getAliases())),
                "command." + commandEntity.getAliases().getFirst() + ".permission", commandEntity.getPermission(),
                "command." + commandEntity.getAliases().getFirst() + ".description", commandEntity.getDescription(),
                "command." + commandEntity.getAliases().getFirst() + ".usage", commandEntity.getUsage()
        );
    }
}
