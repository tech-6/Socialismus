package me.whereareiam.socialismus.platform.velocity;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import me.whereareiam.socialismus.api.model.DummyPlayer;
import me.whereareiam.socialismus.api.output.PlatformMessenger;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;

import java.util.Optional;

@Singleton
public class VelocityPlatformMessenger implements PlatformMessenger {
	private final ProxyServer proxyServer;

	@Inject
	public VelocityPlatformMessenger(ProxyServer proxyServer) {
		this.proxyServer = proxyServer;
	}

	@Override
	public void sendMessage(DummyPlayer dummyPlayer, Component component) {
		Optional<Player> player = proxyServer.getPlayer(dummyPlayer.getUniqueId());
		player.ifPresent(value -> value.sendMessage(component));
	}

	@Override
	public void sendActionBar(DummyPlayer dummyPlayer, Component component) {
		Optional<Player> player = proxyServer.getPlayer(dummyPlayer.getUniqueId());
		player.ifPresent(value -> value.sendActionBar(component));
	}

	@Override
	public void sendBossBar(DummyPlayer dummyPlayer, BossBar bossBar) {
		Optional<Player> player = proxyServer.getPlayer(dummyPlayer.getUniqueId());
		player.ifPresent(value -> value.showBossBar(bossBar));
	}

	@Override
	public void sendTitle(DummyPlayer dummyPlayer, Title title) {
		Optional<Player> player = proxyServer.getPlayer(dummyPlayer.getUniqueId());
		player.ifPresent(value -> value.showTitle(title));
	}

	@Override
	public void broadcastMessage(Component component) {
		proxyServer.sendMessage(component);
	}
}
