package me.whereareiam.socialismus.platform.bukkit.inject;

import com.google.inject.AbstractModule;
import me.whereareiam.socialismus.api.input.DependencyResolver;
import me.whereareiam.socialismus.api.output.ListenerRegistrar;
import me.whereareiam.socialismus.api.output.LoggingHelper;
import me.whereareiam.socialismus.api.output.Scheduler;
import me.whereareiam.socialismus.api.output.platform.PlatformInteractor;
import me.whereareiam.socialismus.api.output.platform.PlatformMessenger;
import me.whereareiam.socialismus.platform.bukkit.*;
import me.whereareiam.socialismus.platform.bukkit.listener.BukkitListenerRegistrar;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

public class BukkitInjectorConfiguration extends AbstractModule {
	private final Plugin plugin;
	private final BukkitDependencyResolver dependencyResolver;

	public BukkitInjectorConfiguration(Plugin plugin, BukkitDependencyResolver dependencyResolver) {
		this.plugin = plugin;
		this.dependencyResolver = dependencyResolver;
	}

	@Override
	protected void configure() {
		bind(Plugin.class).toInstance(plugin);
		bind(PluginManager.class).toInstance(plugin.getServer().getPluginManager());
		bind(DependencyResolver.class).toInstance(dependencyResolver);

		bind(LoggingHelper.class).to(BukkitLoggingHelper.class);
		bind(Scheduler.class).to(BukkitScheduler.class);
		bind(ListenerRegistrar.class).to(BukkitListenerRegistrar.class);
		bind(PlatformMessenger.class).to(BukkitPlatformMessenger.class);
		bind(PlatformInteractor.class).to(BukkitPlatformInteractor.class);
	}
}
