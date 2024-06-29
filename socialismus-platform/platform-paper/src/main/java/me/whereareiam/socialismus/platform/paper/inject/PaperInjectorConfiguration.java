package me.whereareiam.socialismus.platform.paper.inject;

import com.google.inject.AbstractModule;
import me.whereareiam.socialismus.api.input.DependencyResolver;
import me.whereareiam.socialismus.api.model.player.DummyPlayer;
import me.whereareiam.socialismus.api.output.ListenerRegistrar;
import me.whereareiam.socialismus.api.output.LoggingHelper;
import me.whereareiam.socialismus.api.output.Scheduler;
import me.whereareiam.socialismus.api.output.platform.PlatformInteractor;
import me.whereareiam.socialismus.api.output.platform.PlatformMessenger;
import me.whereareiam.socialismus.platform.paper.*;
import me.whereareiam.socialismus.platform.paper.listener.PaperListenerRegistrar;
import me.whereareiam.socialismus.platform.paper.mapper.CommandSourceStackMapper;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.incendo.cloud.CommandManager;
import org.incendo.cloud.execution.ExecutionCoordinator;
import org.incendo.cloud.paper.PaperCommandManager;

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
		bind(PluginManager.class).toInstance(plugin.getServer().getPluginManager());
		bind(DependencyResolver.class).toInstance(dependencyResolver);

		bind(LoggingHelper.class).to(PaperLoggingHelper.class);
		bind(Scheduler.class).to(PaperScheduler.class);
		bind(ListenerRegistrar.class).to(PaperListenerRegistrar.class);
		bind(PlatformMessenger.class).to(PaperPlatformMessenger.class);
		bind(PlatformInteractor.class).to(PaperPlatformInteractor.class);

		bindCommandManager();
	}

	@SuppressWarnings("UnstableApiUsage")
	private void bindCommandManager() {
		PaperCommandManager<DummyPlayer> commandManager = PaperCommandManager
				.builder(new CommandSourceStackMapper())
				.executionCoordinator(ExecutionCoordinator.asyncCoordinator())
				.buildOnEnable(plugin);

		bind(CommandManager.class).toInstance(commandManager);
	}
}
