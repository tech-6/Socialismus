package me.whereareiam.socialismus.platform.velocity.listener;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.velocitypowered.api.event.EventManager;
import com.velocitypowered.api.proxy.ProxyServer;
import me.whereareiam.socialismus.api.output.ListenerRegistrar;
import me.whereareiam.socialismus.platform.velocity.listener.chat.PlayerChatListener;

import java.util.stream.Stream;

@Singleton
public class VelocityListenerRegistrar implements ListenerRegistrar {
	private final Injector injector;
	private final ProxyServer proxyServer;
	private final EventManager eventManager;

	@Inject
	public VelocityListenerRegistrar(Injector injector, ProxyServer proxyServer, EventManager eventManager) {
		this.injector = injector;
		this.proxyServer = proxyServer;
		this.eventManager = eventManager;
	}

	@Override
	public void registerListeners() {
		Stream.of(
				injector.getInstance(PlayerChatListener.class)
		).forEach(this::registerListener);
	}

	@Override
	public void registerListener(Object listener) {
		eventManager.register(proxyServer, listener);
	}
}
