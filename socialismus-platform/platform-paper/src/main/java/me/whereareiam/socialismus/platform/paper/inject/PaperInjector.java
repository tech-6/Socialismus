package me.whereareiam.socialismus.platform.paper.inject;

import com.google.inject.Guice;
import com.google.inject.Injector;
import lombok.Getter;
import me.whereareiam.socialismus.adapter.config.ConfigBinder;
import me.whereareiam.socialismus.platform.paper.PaperDependencyResolver;

import java.nio.file.Path;

@Getter
public class PaperInjector {
	private final Injector injector;

	public PaperInjector(PaperDependencyResolver dependencyResolver, Path dataPath) {
		this.injector = Guice.createInjector(new PaperInjectorConfiguration(dependencyResolver), new ConfigBinder(dataPath));
	}
}
