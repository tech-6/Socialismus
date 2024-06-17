package me.whereareiam.socialismus.platform.velocity.inject;

import com.google.inject.AbstractModule;
import me.whereareiam.socialismus.api.input.DependencyResolver;
import me.whereareiam.socialismus.api.output.LoggingHelper;
import me.whereareiam.socialismus.platform.velocity.VelocityDependencyResolver;
import me.whereareiam.socialismus.platform.velocity.VelocityLoggingHelper;

public class VelocityInjectorConfiguration extends AbstractModule {
	private final VelocityDependencyResolver dependencyResolver;

	public VelocityInjectorConfiguration(VelocityDependencyResolver dependencyResolver) {
		this.dependencyResolver = dependencyResolver;
	}

	@Override
	protected void configure() {
		bind(DependencyResolver.class).toInstance(dependencyResolver);

		bind(LoggingHelper.class).to(VelocityLoggingHelper.class);
	}
}
