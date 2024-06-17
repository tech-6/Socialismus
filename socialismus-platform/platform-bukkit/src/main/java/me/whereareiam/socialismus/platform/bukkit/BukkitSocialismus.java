package me.whereareiam.socialismus.platform.bukkit;

import me.whereareiam.socialismus.common.base.SocialismusBase;
import org.bukkit.plugin.java.JavaPlugin;

import java.nio.file.Path;
import java.util.logging.Logger;

@SuppressWarnings("unused")
public class BukkitSocialismus extends JavaPlugin {
	private final SocialismusBase socialismusBase = new SocialismusBase();
	private final Path dataPath = getDataFolder().toPath();
	private final Logger logger = getLogger();

	private BukkitDependencyResolver dependencyResolver;

	@Override
	public void onLoad() {
		dependencyResolver = new BukkitDependencyResolver(this);
		dependencyResolver.loadLibraries();
		dependencyResolver.resolveDependencies();
	}

	@Override
	public void onEnable() {
		if (!dataPath.toFile().exists()) {
			if (!dataPath.toFile().mkdir()) {
				logger.severe("Failed to create data folder. Disabling proxy.");
				this.getServer().shutdown();
			}
		}

		new BukkitInjector(dependencyResolver, dataPath);
		socialismusBase.onEnable();
	}

	@Override
	public void onDisable() {
		socialismusBase.onDisable();

	}
}
