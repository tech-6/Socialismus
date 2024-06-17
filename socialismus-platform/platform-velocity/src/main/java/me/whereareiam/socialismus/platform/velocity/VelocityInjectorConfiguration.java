package me.whereareiam.socialismus.platform.velocity;

import com.google.inject.AbstractModule;
import me.whereareiam.socialismus.api.input.DependencyResolver;

public class VelocityInjectorConfiguration extends AbstractModule {
	private final VelocityDependencyResolver dependencyResolver;

	public VelocityInjectorConfiguration(VelocityDependencyResolver dependencyResolver) {
		this.dependencyResolver = dependencyResolver;
	}

	@Override
	protected void configure() {
		bind(DependencyResolver.class).toInstance(dependencyResolver);
	}
}
