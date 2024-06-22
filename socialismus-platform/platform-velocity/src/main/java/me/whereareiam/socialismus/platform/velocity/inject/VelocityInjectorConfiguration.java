package me.whereareiam.socialismus.platform.velocity.inject;

import com.google.inject.AbstractModule;
import com.velocitypowered.api.event.EventManager;
import com.velocitypowered.api.proxy.ProxyServer;
import me.whereareiam.socialismus.api.input.DependencyResolver;
import me.whereareiam.socialismus.api.output.ListenerRegistrar;
import me.whereareiam.socialismus.api.output.LoggingHelper;
import me.whereareiam.socialismus.api.output.Scheduler;
import me.whereareiam.socialismus.api.output.platform.PlatformInteractor;
import me.whereareiam.socialismus.api.output.platform.PlatformMessenger;
import me.whereareiam.socialismus.platform.velocity.*;
import me.whereareiam.socialismus.platform.velocity.listener.VelocityListenerRegistrar;

public class VelocityInjectorConfiguration extends AbstractModule {
	private final ProxyServer proxyServer;
	private final VelocityDependencyResolver dependencyResolver;

	public VelocityInjectorConfiguration(ProxyServer proxyServer, VelocityDependencyResolver dependencyResolver) {
		this.proxyServer = proxyServer;
		this.dependencyResolver = dependencyResolver;
	}

	@Override
	protected void configure() {
		bind(ProxyServer.class).toInstance(proxyServer);
		bind(EventManager.class).toInstance(proxyServer.getEventManager());
		bind(DependencyResolver.class).toInstance(dependencyResolver);

		bind(LoggingHelper.class).to(VelocityLoggingHelper.class);
		bind(Scheduler.class).to(VelocityScheduler.class);
		bind(ListenerRegistrar.class).to(VelocityListenerRegistrar.class);
		bind(PlatformMessenger.class).to(VelocityPlatformMessenger.class);
		bind(PlatformInteractor.class).to(VelocityPlatformInteractor.class);
	}
}