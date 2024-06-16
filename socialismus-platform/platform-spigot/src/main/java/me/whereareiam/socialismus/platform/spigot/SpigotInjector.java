package me.whereareiam.socialismus.platform.spigot;

import com.google.inject.Guice;
import com.google.inject.Injector;
import lombok.Getter;
import me.whereareiam.socialismus.adapter.config.ConfigInitializer;

import java.nio.file.Path;

@Getter
public class SpigotInjector {
	private final Injector injector;

	public SpigotInjector(Path dataPath) {
		this.injector = Guice.createInjector(new SpigotInjectorConfiguration(), new ConfigInitializer(dataPath));
	}
}
