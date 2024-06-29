package me.whereareiam.socialismus.platform.paper;

import me.whereareiam.socialismus.api.model.player.DummyPlayer;
import me.whereareiam.socialismus.api.output.platform.PlatformMessenger;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PaperPlatformMessenger implements PlatformMessenger {
	@Override
	public void sendMessage(DummyPlayer dummyPlayer, Component component) {
		Player player = Bukkit.getPlayer(dummyPlayer.getUniqueId());
		if (player == null) return;

		player.sendMessage(component);
	}

	@Override
	public void sendActionBar(DummyPlayer dummyPlayer, Component component) {
		Player player = Bukkit.getPlayer(dummyPlayer.getUniqueId());
		if (player == null) return;

		player.sendActionBar(component);
	}

	@Override
	public void sendBossBar(DummyPlayer dummyPlayer, BossBar bossBar) {
		Player player = Bukkit.getPlayer(dummyPlayer.getUniqueId());
		if (player == null) return;

		bossBar.addViewer(player);
	}

	@Override
	public void sendTitle(DummyPlayer dummyPlayer, Title title) {
		Player player = Bukkit.getPlayer(dummyPlayer.getUniqueId());
		if (player == null) return;

		player.showTitle(title);
	}

	@Override
	public void broadcastMessage(Component component) {
		Bukkit.broadcast(component);
	}
}
