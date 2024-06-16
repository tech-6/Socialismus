package me.whereareiam.socialismus.platform.paper;

import me.whereareiam.socialismus.common.SocialismusBase;
import org.bukkit.plugin.java.JavaPlugin;

import java.nio.file.Path;
import java.util.logging.Logger;

@SuppressWarnings("unused")
public class PaperSocialismus extends JavaPlugin {
	private final SocialismusBase socialismusBase = new SocialismusBase();
	private final Path dataPath = getDataFolder().toPath();
	private final Logger logger = getLogger();

	@Override
	public void onEnable() {
		if (!dataPath.toFile().exists()) {
			if (!dataPath.toFile().mkdir()) {
				logger.severe("Failed to create data folder. Disabling proxy.");
				this.getServer().shutdown();
			}
		}

		new PaperInjector(dataPath);
		socialismusBase.onEnable();
	}

	@Override
	public void onDisable() {
		socialismusBase.onDisable();
	}
}
