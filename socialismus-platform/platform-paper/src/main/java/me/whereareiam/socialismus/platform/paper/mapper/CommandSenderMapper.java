package me.whereareiam.socialismus.platform.paper.mapper;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.whereareiam.socialismus.api.input.container.PlayerContainerService;
import me.whereareiam.socialismus.api.model.player.DummyCommandPlayer;
import me.whereareiam.socialismus.api.model.player.DummyPlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.incendo.cloud.SenderMapper;

import javax.annotation.Nonnull;

@Singleton
public class CommandSenderMapper implements SenderMapper<CommandSender, DummyPlayer> {
    private final PlayerContainerService playerContainer;

    @Inject
    public CommandSenderMapper(PlayerContainerService playerContainer) {
        this.playerContainer = playerContainer;
    }

    @Override
    public @NonNull DummyPlayer map(@NonNull CommandSender source) {
        if (source instanceof ConsoleCommandSender) {
            return DummyCommandPlayer.builder().commandSender(source).audience(source).build();
        }

        return playerContainer.getPlayer(source.getName())
                .map(dummyPlayer -> {
                    ((DummyCommandPlayer) dummyPlayer).setCommandSender(source);
                    return dummyPlayer;
                })
                .orElseThrow(
                        () -> new NullPointerException("A player with the name " + source.getName() + " was not found")
                );
    }

    @Override
    public @Nonnull CommandSender reverse(final @Nonnull DummyPlayer dummyPlayer) {
        return (CommandSender) ((DummyCommandPlayer) dummyPlayer).getCommandSender();
    }
}
