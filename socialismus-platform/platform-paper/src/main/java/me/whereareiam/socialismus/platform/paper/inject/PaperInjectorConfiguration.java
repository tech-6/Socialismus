package me.whereareiam.socialismus.platform.paper.inject;

import com.google.inject.AbstractModule;
import me.whereareiam.socialismus.api.input.DependencyResolver;
import me.whereareiam.socialismus.api.output.*;
import me.whereareiam.socialismus.platform.paper.*;
import me.whereareiam.socialismus.platform.paper.listener.PaperListenerRegistrar;
import org.bukkit.plugin.Plugin;

public class PaperInjectorConfiguration extends AbstractModule {
	private final Plugin plugin;
	private final PaperDependencyResolver dependencyResolver;

	public PaperInjectorConfiguration(Plugin plugin, PaperDependencyResolver dependencyResolver) {
		this.plugin = plugin;
		this.dependencyResolver = dependencyResolver;
	}

	@Override
	protected void configure() {
		bind(Plugin.class).toInstance(plugin);
		bind(DependencyResolver.class).toInstance(dependencyResolver);

		bind(LoggingHelper.class).to(PaperLoggingHelper.class);
		bind(Scheduler.class).to(PaperScheduler.class);
		bind(ListenerRegistrar.class).to(PaperListenerRegistrar.class);
		bind(PlatformMessenger.class).to(PaperPlatformMessenger.class);
		bind(PlatformInteractor.class).to(PaperPlatformInteractor.class);
	}
}
