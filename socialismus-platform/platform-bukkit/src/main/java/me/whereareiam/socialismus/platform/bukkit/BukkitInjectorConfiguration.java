package me.whereareiam.socialismus.platform.bukkit;

import com.google.inject.AbstractModule;
import me.whereareiam.socialismus.api.input.DependencyResolver;

public class BukkitInjectorConfiguration extends AbstractModule {
	private final BukkitDependencyResolver dependencyResolver;

	public BukkitInjectorConfiguration(BukkitDependencyResolver dependencyResolver) {
		this.dependencyResolver = dependencyResolver;
	}

	@Override
	protected void configure() {
		bind(DependencyResolver.class).toInstance(dependencyResolver);
	}
}
