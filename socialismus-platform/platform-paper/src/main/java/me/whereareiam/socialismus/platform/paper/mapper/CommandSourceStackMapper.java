package me.whereareiam.socialismus.platform.paper.mapper;

import com.google.inject.Singleton;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import me.whereareiam.socialismus.api.model.player.DummyCommandPlayer;
import me.whereareiam.socialismus.api.model.player.DummyPlayer;
import net.kyori.adventure.audience.Audience;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.incendo.cloud.SenderMapper;

import javax.annotation.Nonnull;

@SuppressWarnings("UnstableApiUsage")
@Singleton
public class CommandSourceStackMapper implements SenderMapper<CommandSourceStack, DummyPlayer> {
	@Override
	public @NonNull DummyPlayer map(@NonNull CommandSourceStack source) {
		DummyPlayer dummyPlayer;

		if (source.getSender() instanceof ConsoleCommandSender || source.getSender().getClass().getName().equals("io.papermc.paper.brigadier.NullCommandSender")) {
			dummyPlayer = DummyCommandPlayer.builder().commandSender(source).audience(source.getSender()).build();
		} else {
			Player player = Bukkit.getPlayer(source.getSender().getName());
			if (player == null)
				throw new NullPointerException("A player with the name " + source.getSender().getName() + " was not found");

			dummyPlayer = DummyCommandPlayer.builder()
					.commandSender(source)
					.username(player.getName())
					.uniqueId(player.getUniqueId())
					.audience((Audience) player)
					.location(player.getWorld().getName())
					.locale(player.locale())
					.build();
		}

		return dummyPlayer;
	}

	@Override
	public @Nonnull CommandSourceStack reverse(@Nonnull DummyPlayer dummyPlayer) {
		return (CommandSourceStack) ((DummyCommandPlayer) dummyPlayer).getCommandSender();
	}
}
