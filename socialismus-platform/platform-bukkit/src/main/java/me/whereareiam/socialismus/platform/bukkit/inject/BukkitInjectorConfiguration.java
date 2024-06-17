package me.whereareiam.socialismus.platform.bukkit.inject;

import com.google.inject.AbstractModule;
import me.whereareiam.socialismus.api.input.DependencyResolver;
import me.whereareiam.socialismus.api.output.LoggingHelper;
import me.whereareiam.socialismus.platform.bukkit.BukkitDependencyResolver;
import me.whereareiam.socialismus.platform.bukkit.BukkitLoggingHelper;

public class BukkitInjectorConfiguration extends AbstractModule {
	private final BukkitDependencyResolver dependencyResolver;

	public BukkitInjectorConfiguration(BukkitDependencyResolver dependencyResolver) {
		this.dependencyResolver = dependencyResolver;
	}

	@Override
	protected void configure() {
		bind(DependencyResolver.class).toInstance(dependencyResolver);

		bind(LoggingHelper.class).to(BukkitLoggingHelper.class);
	}
}
