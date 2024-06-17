package me.whereareiam.socialismus.platform.bukkit.inject;

import com.google.inject.Guice;
import com.google.inject.Injector;
import lombok.Getter;
import me.whereareiam.socialismus.adapter.config.ConfigBinder;
import me.whereareiam.socialismus.common.CommonInjector;
import me.whereareiam.socialismus.platform.bukkit.BukkitDependencyResolver;

import java.nio.file.Path;

@Getter
public class BukkitInjector {
	public BukkitInjector(BukkitDependencyResolver dependencyResolver, Path dataPath) {
		Injector injector = Guice.createInjector(
				new BukkitInjectorConfiguration(dependencyResolver),
				new ConfigBinder(dataPath)
		);

		CommonInjector.setInjector(injector);
	}
}
