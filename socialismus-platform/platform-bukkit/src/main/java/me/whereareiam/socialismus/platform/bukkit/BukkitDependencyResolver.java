package me.whereareiam.socialismus.platform.bukkit;

import com.alessiodp.libby.BukkitLibraryManager;
import com.alessiodp.libby.Library;
import com.alessiodp.libby.relocation.Relocation;
import me.whereareiam.socialismus.common.CommonDependencyResolver;
import me.whereareiam.socialismus.common.Constants;
import org.bukkit.plugin.Plugin;

public class BukkitDependencyResolver extends CommonDependencyResolver {
    private final BukkitLibraryManager libraryManager;

    public BukkitDependencyResolver(Plugin plugin) {
        this.libraryManager = new BukkitLibraryManager(plugin, ".libraries");
    }

    @Override
    public void resolveDependencies() {
        libraryManager.addMavenCentral();
        libraryManager.addJitPack();

        libraries.forEach(libraryManager::loadLibrary);
        clearDependencies();
    }

    @Override
    public void loadLibraries() {
        super.loadLibraries();

        // Bukkit specific libraries
        addDependency(Library.builder()
                .groupId("net{}kyori")
                .artifactId("adventure-platform-bukkit")
                .version(Constants.getAdventureBukkitVersion())
                .resolveTransitiveDependencies(true)
                .relocate(Relocation.builder()
                        .pattern("net{}kyori")
                        .relocatedPattern("me.whereareiam.socialismus.library")
                        .build())
                .build());

        addDependency(Library.builder()
                .groupId("org{}incendo")
                .artifactId("cloud-paper")
                .version(Constants.getCloudPaperVersion())
                .resolveTransitiveDependencies(true)
                .build());
    }
}
