package me.whereareiam.socialismus.platform.bukkit.mapper;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.whereareiam.socialismus.api.model.player.DummyCommandPlayer;
import me.whereareiam.socialismus.api.model.player.DummyPlayer;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.incendo.cloud.SenderMapper;

import javax.annotation.Nonnull;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;

@Singleton
public class CommandSenderMapper implements SenderMapper<CommandSender, DummyPlayer> {
    private final BukkitAudiences audiences;
    private final Plugin plugin;

    @Inject
    public CommandSenderMapper(BukkitAudiences audiences, Plugin plugin) {
        this.audiences = audiences;
        this.plugin = plugin;
    }

    @Override
    public @NonNull DummyPlayer map(@NonNull CommandSender source) {
        if (source instanceof ConsoleCommandSender)
            return mapConsoleCommandSender(source);

        return mapPlayerCommandSender(source);
    }

    private DummyPlayer mapConsoleCommandSender(CommandSender source) {
        return DummyCommandPlayer.builder()
                .commandSender(source)
                .audience(audiences.sender(source))
                .build();
    }

    private DummyPlayer mapPlayerCommandSender(CommandSender source) {
        Player player = Bukkit.getPlayer(source.getName());
        if (player != null) return buildDummyPlayer(source, player);

        return getDelayedDummyPlayer(source);
    }

    private DummyPlayer buildDummyPlayer(CommandSender source, Player player) {
        return DummyCommandPlayer.builder()
                .commandSender(source)
                .username(player.getName())
                .uniqueId(player.getUniqueId())
                .audience(audiences.sender(player))
                .location(player.getWorld().getName())
                .locale(Locale.forLanguageTag(player.getLocale()))
                .build();
    }

    private DummyPlayer getDelayedDummyPlayer(CommandSender source) {
        CompletableFuture<DummyPlayer> future = new CompletableFuture<>();
        Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, () -> {
            Player delayedPlayer = Bukkit.getPlayer(source.getName());
            if (delayedPlayer != null) {
                future.complete(buildDummyPlayer(source, delayedPlayer));
            } else {
                future.completeExceptionally(new IllegalStateException("Player not found"));
            }
        }, 20);
        try {
            return future.get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public @Nonnull CommandSender reverse(final @Nonnull DummyPlayer dummyPlayer) {
        return (CommandSender) ((DummyCommandPlayer) dummyPlayer).getCommandSender();
    }
}