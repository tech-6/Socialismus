package me.whereareiam.socialismus.platform.paper;

import com.google.inject.AbstractModule;
import me.whereareiam.socialismus.api.input.DependencyResolver;

public class PaperInjectorConfiguration extends AbstractModule {
	private final PaperDependencyResolver dependencyResolver;

	public PaperInjectorConfiguration(PaperDependencyResolver dependencyResolver) {
		this.dependencyResolver = dependencyResolver;
	}

	@Override
	protected void configure() {
		bind(DependencyResolver.class).toInstance(dependencyResolver);
	}
}
