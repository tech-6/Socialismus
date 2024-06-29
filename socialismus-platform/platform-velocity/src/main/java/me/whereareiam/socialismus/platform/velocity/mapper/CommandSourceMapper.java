package me.whereareiam.socialismus.platform.velocity.mapper;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.ConsoleCommandSource;
import com.velocitypowered.api.proxy.Player;
import me.whereareiam.socialismus.api.model.player.DummyCommandPlayer;
import me.whereareiam.socialismus.api.model.player.DummyPlayer;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.incendo.cloud.SenderMapper;

import javax.annotation.Nonnull;

public class CommandSourceMapper implements SenderMapper<CommandSource, DummyPlayer> {
	@Override
	public @NonNull DummyPlayer map(@NonNull CommandSource source) {
		DummyPlayer dummyPlayer;
		if (source instanceof ConsoleCommandSource) {
			dummyPlayer = DummyCommandPlayer.builder().commandSender(source).build();
		} else {
			Player player = (Player) source;

			dummyPlayer = DummyCommandPlayer.builder()
					.commandSender(source)
					.username(player.getUsername())
					.uniqueId(player.getUniqueId())
					.location(player.getCurrentServer().map(serverConnection -> serverConnection.getServerInfo().getName()).orElse(null))
					.locale(player.getEffectiveLocale())
					.build();
		}

		return dummyPlayer;
	}

	@Override
	public @Nonnull CommandSource reverse(final @Nonnull DummyPlayer dummyPlayer) {
		return (CommandSource) ((DummyCommandPlayer) dummyPlayer).getCommandSender();
	}
}
