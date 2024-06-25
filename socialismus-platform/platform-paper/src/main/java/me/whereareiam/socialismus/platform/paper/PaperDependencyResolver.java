package me.whereareiam.socialismus.platform.paper;

import com.alessiodp.libby.PaperLibraryManager;
import me.whereareiam.socialismus.common.CommonDependencyResolver;
import org.bukkit.plugin.Plugin;

public class PaperDependencyResolver extends CommonDependencyResolver {
	private final PaperLibraryManager libraryManager;

	public PaperDependencyResolver(Plugin plugin) {
		this.libraryManager = new PaperLibraryManager(plugin, ".libraries");
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

		// Paper specific libraries
	}
}
