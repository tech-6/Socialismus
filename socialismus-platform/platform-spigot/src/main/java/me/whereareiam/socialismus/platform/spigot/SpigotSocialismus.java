package me.whereareiam.socialismus.platform.spigot;

import com.saicone.ezlib.Ezlib;
import me.whereareiam.socialismus.common.SocialismusBase;
import org.bukkit.plugin.java.JavaPlugin;

import java.nio.file.Path;
import java.util.logging.Logger;

@SuppressWarnings("unused")
public class SpigotSocialismus extends JavaPlugin {
	private final SocialismusBase socialismusBase = new SocialismusBase();
	private final Path dataPath = getDataFolder().toPath();
	private final Logger logger = getLogger();

	@Override
	public void onLoad() {
		Ezlib ezlib = new Ezlib();
		ezlib.init();

		ezlib.dependency("com.google.inject:guice:7.0.0").load();
	}

	@Override
	public void onEnable() {
		if (!dataPath.toFile().exists()) {
			if (!dataPath.toFile().mkdir()) {
				logger.severe("Failed to create data folder. Disabling proxy.");
				this.getServer().shutdown();
			}
		}

		new SpigotInjector(dataPath);
		socialismusBase.onEnable();
	}

	@Override
	public void onDisable() {
		socialismusBase.onDisable();

	}
}
