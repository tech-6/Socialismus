package me.whereareiam.socialismus.platform.velocity.inject;

import com.google.inject.Guice;
import com.google.inject.Injector;
import lombok.Getter;
import me.whereareiam.socialismus.adapter.config.ConfigBinder;
import me.whereareiam.socialismus.platform.velocity.VelocityDependencyResolver;

import java.nio.file.Path;

@Getter
public class VelocityInjector {
	private final Injector injector;

	public VelocityInjector(VelocityDependencyResolver dependencyResolver, Path dataPath) {
		this.injector = Guice.createInjector(new VelocityInjectorConfiguration(dependencyResolver), new ConfigBinder(dataPath));
	}
}
