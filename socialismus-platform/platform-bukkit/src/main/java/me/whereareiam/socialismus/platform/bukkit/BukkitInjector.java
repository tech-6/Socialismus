package me.whereareiam.socialismus.platform.bukkit;

import com.google.inject.Guice;
import com.google.inject.Injector;
import lombok.Getter;
import me.whereareiam.socialismus.adapter.config.ConfigInitializer;

import java.nio.file.Path;

@Getter
public class BukkitInjector {
	private final Injector injector;

	public BukkitInjector(BukkitDependencyResolver dependencyResolver, Path dataPath) {
		this.injector = Guice.createInjector(new BukkitInjectorConfiguration(dependencyResolver), new ConfigInitializer(dataPath));
	}
}
