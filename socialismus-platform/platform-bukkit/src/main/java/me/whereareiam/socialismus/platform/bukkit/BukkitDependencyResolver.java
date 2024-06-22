package me.whereareiam.socialismus.platform.bukkit;

import com.alessiodp.libby.BukkitLibraryManager;
import me.whereareiam.socialismus.common.CommonDependencyResolver;
import me.whereareiam.socialismus.common.Constants;
import org.bukkit.plugin.Plugin;

public class BukkitDependencyResolver extends CommonDependencyResolver {
	private final BukkitLibraryManager libraryManager;

	public BukkitDependencyResolver(Plugin plugin) {
		this.libraryManager = new BukkitLibraryManager(plugin, "libraries");
	}

	@Override
	public void resolveDependencies() {
		libraryManager.addMavenCentral();
		libraries.forEach(libraryManager::loadLibrary);
		clearDependencies();
	}

	@Override
	public void loadLibraries() {
		super.loadLibraries();

		// Bukkit specific libraries
		addDependency("net.kyori", "adventure-platform-bukkit", Constants.getAdventureBukkitVersion(), true);
	}
}
