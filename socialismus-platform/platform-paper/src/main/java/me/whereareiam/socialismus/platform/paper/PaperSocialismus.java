package me.whereareiam.socialismus.platform.paper;

import me.whereareiam.socialismus.common.base.CommonSocialismus;
import me.whereareiam.socialismus.platform.paper.inject.PaperInjector;
import org.bukkit.plugin.java.JavaPlugin;

import java.nio.file.Path;
import java.util.logging.Logger;

@SuppressWarnings("unused")
public class PaperSocialismus extends JavaPlugin {
	private final CommonSocialismus commonSocialismus = new CommonSocialismus();
	private final Path dataPath = getDataFolder().toPath();
	private final Logger logger = getLogger();

	private PaperDependencyResolver dependencyResolver;

	@Override
	public void onLoad() {
		dependencyResolver = new PaperDependencyResolver(this);
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

		new PaperInjector(this, dependencyResolver, dataPath);
		PaperLoggingHelper.setLogger(logger);

		commonSocialismus.onEnable();
	}

	@Override
	public void onDisable() {
		commonSocialismus.onDisable();
	}
}
