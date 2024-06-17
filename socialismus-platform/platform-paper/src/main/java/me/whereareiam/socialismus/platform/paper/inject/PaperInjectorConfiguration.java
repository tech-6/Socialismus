package me.whereareiam.socialismus.platform.paper.inject;

import com.google.inject.AbstractModule;
import me.whereareiam.socialismus.api.input.DependencyResolver;
import me.whereareiam.socialismus.api.output.LoggingHelper;
import me.whereareiam.socialismus.platform.paper.PaperDependencyResolver;
import me.whereareiam.socialismus.platform.paper.PaperLoggingHelper;

public class PaperInjectorConfiguration extends AbstractModule {
	private final PaperDependencyResolver dependencyResolver;

	public PaperInjectorConfiguration(PaperDependencyResolver dependencyResolver) {
		this.dependencyResolver = dependencyResolver;
	}

	@Override
	protected void configure() {
		bind(DependencyResolver.class).toInstance(dependencyResolver);

		bind(LoggingHelper.class).to(PaperLoggingHelper.class);
	}
}
