package me.whereareiam.socialismus.platform.bukkit.mapper;

import me.whereareiam.socialismus.api.model.player.DummyCommandPlayer;
import me.whereareiam.socialismus.api.model.player.DummyPlayer;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.incendo.cloud.SenderMapper;

import javax.annotation.Nonnull;
import java.util.Locale;

public class CommandSenderMapper implements SenderMapper<CommandSender, DummyPlayer> {
	@Override
	public @NonNull DummyPlayer map(@NonNull CommandSender source) {
		DummyPlayer dummyPlayer;
		if (source instanceof ConsoleCommandSender) {
			dummyPlayer = DummyCommandPlayer.builder().commandSender(source).build();
		} else {
			Player player = Bukkit.getPlayer(source.getName());
			if (player == null)
				throw new NullPointerException("A player with the name " + source.getName() + " was not found");

			dummyPlayer = DummyCommandPlayer.builder()
					.commandSender(source)
					.username(player.getName())
					.uniqueId(player.getUniqueId())
					.location(player.getWorld().getName())
					.locale(Locale.of(player.getLocale()))
					.build();
		}

		return dummyPlayer;
	}

	@Override
	public @Nonnull CommandSender reverse(final @Nonnull DummyPlayer dummyPlayer) {
		return (CommandSender) ((DummyCommandPlayer) dummyPlayer).getCommandSender();
	}
}
