package me.whereareiam.socialismus.platform.velocity;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.velocitypowered.api.proxy.ProxyServer;
import me.whereareiam.socialismus.api.model.DummyPlayer;
import me.whereareiam.socialismus.api.output.PlatformInteractor;

@Singleton
public class VelocityPlatformInteractor implements PlatformInteractor {
	private final ProxyServer proxyServer;

	@Inject
	public VelocityPlatformInteractor(ProxyServer proxyServer) {
		this.proxyServer = proxyServer;
	}

	@Override
	public boolean hasPermission(DummyPlayer dummyPlayer, String permission) {
		return proxyServer
				.getPlayer(dummyPlayer.getUniqueId())
				.map(value -> value.hasPermission(permission))
				.orElse(false);

	}
}
