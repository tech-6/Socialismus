package me.whereareiam.socialismus.platform.bukkit.inject;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import me.whereareiam.socialismus.api.input.DependencyResolver;
import me.whereareiam.socialismus.api.model.player.DummyPlayer;
import me.whereareiam.socialismus.api.output.ListenerRegistrar;
import me.whereareiam.socialismus.api.output.LoggingHelper;
import me.whereareiam.socialismus.api.output.PlatformInteractor;
import me.whereareiam.socialismus.api.output.Scheduler;
import me.whereareiam.socialismus.platform.bukkit.*;
import me.whereareiam.socialismus.platform.bukkit.listener.BukkitListenerRegistrar;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.incendo.cloud.CommandManager;

public class BukkitInjectorConfiguration extends AbstractModule {
	private final Plugin plugin;
	private final BukkitDependencyResolver dependencyResolver;
	private final BukkitAudiences audiences;

	public BukkitInjectorConfiguration(Plugin plugin, BukkitDependencyResolver dependencyResolver) {
		this.plugin = plugin;
		this.dependencyResolver = dependencyResolver;

		this.audiences = BukkitAudiences.create(plugin);
	}

	@Override
	protected void configure() {
		bind(Plugin.class).toInstance(plugin);
		bind(PluginManager.class).toInstance(plugin.getServer().getPluginManager());
		bind(BukkitAudiences.class).toInstance(audiences);
		bind(DependencyResolver.class).toInstance(dependencyResolver);

		bind(LoggingHelper.class).to(BukkitLoggingHelper.class);
		bind(Scheduler.class).to(BukkitScheduler.class);
		bind(ListenerRegistrar.class).to(BukkitListenerRegistrar.class);
		bind(PlatformInteractor.class).to(BukkitPlatformInteractor.class);
		bind(new TypeLiteral<CommandManager<DummyPlayer>>() {}).toProvider(BukkitCommandManagerProvider.class).asEagerSingleton();
	}
}
