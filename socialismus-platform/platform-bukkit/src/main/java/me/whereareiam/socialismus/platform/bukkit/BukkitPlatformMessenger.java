package me.whereareiam.socialismus.platform.bukkit;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.whereareiam.socialismus.api.model.DummyPlayer;
import me.whereareiam.socialismus.api.output.PlatformMessenger;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

@Singleton
public class BukkitPlatformMessenger implements PlatformMessenger {
	private final BukkitAudiences bukkitAudiences;

	@Inject
	public BukkitPlatformMessenger(Plugin plugin) {
		this.bukkitAudiences = BukkitAudiences.create(plugin);
	}

	@Override
	public void sendMessage(DummyPlayer dummyPlayer, Component component) {
		Player player = Bukkit.getPlayer(dummyPlayer.getUniqueId());
		if (player == null) return;

		bukkitAudiences.player(player).sendMessage(component);
	}

	@Override
	public void sendActionBar(DummyPlayer dummyPlayer, Component component) {
		Player player = Bukkit.getPlayer(dummyPlayer.getUniqueId());
		if (player == null) return;

		bukkitAudiences.player(player).sendActionBar(component);
	}

	@Override
	public void sendBossBar(DummyPlayer dummyPlayer, BossBar bossBar) {
		Player player = Bukkit.getPlayer(dummyPlayer.getUniqueId());
		if (player == null) return;

		bukkitAudiences.player(player).showBossBar(bossBar);
	}

	@Override
	public void sendTitle(DummyPlayer dummyPlayer, Title title) {
		Player player = Bukkit.getPlayer(dummyPlayer.getUniqueId());
		if (player == null) return;

		bukkitAudiences.player(player).showTitle(title);
	}

	@Override
	public void broadcastMessage(Component component) {
		bukkitAudiences.players().sendMessage(component);
	}
}
