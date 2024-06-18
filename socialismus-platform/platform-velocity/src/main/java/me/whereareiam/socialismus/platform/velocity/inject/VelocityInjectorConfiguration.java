package me.whereareiam.socialismus.platform.velocity.inject;

import com.google.inject.AbstractModule;
import com.velocitypowered.api.proxy.ProxyServer;
import me.whereareiam.socialismus.api.input.DependencyResolver;
import me.whereareiam.socialismus.api.output.LoggingHelper;
import me.whereareiam.socialismus.api.output.PlatformMessenger;
import me.whereareiam.socialismus.api.output.Scheduler;
import me.whereareiam.socialismus.platform.velocity.VelocityDependencyResolver;
import me.whereareiam.socialismus.platform.velocity.VelocityLoggingHelper;
import me.whereareiam.socialismus.platform.velocity.VelocityPlatformMessenger;
import me.whereareiam.socialismus.platform.velocity.VelocityScheduler;

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
		bind(DependencyResolver.class).toInstance(dependencyResolver);

		bind(LoggingHelper.class).to(VelocityLoggingHelper.class);
		bind(Scheduler.class).to(VelocityScheduler.class);
		bind(PlatformMessenger.class).to(VelocityPlatformMessenger.class);
	}
}