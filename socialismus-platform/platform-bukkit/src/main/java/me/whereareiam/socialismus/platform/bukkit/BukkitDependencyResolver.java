package me.whereareiam.socialismus.platform.bukkit;

import com.alessiodp.libby.BukkitLibraryManager;
import com.alessiodp.libby.Library;
import com.alessiodp.libby.relocation.Relocation;
import me.whereareiam.socialismus.common.CommonDependencyResolver;
import me.whereareiam.socialismus.shared.Constants;
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
                .groupId("com{}google{}inject")
                .artifactId("guice")
                .version(Constants.Dependency.GUICE)
                .resolveTransitiveDependencies(true)
                .relocate(
                        Relocation.builder()
                                .pattern("com{}google{}inject")
                                .relocatedPattern("me.whereareiam.socialismus.library.guice")
                                .build()
                ).relocate(
                        Relocation.builder()
                                .pattern("com{}google{}common")
                                .relocatedPattern("me.whereareiam.socialismus.library.guava")
                                .build()
                ).build());

        addDependency(Library.builder()
                .groupId("net{}kyori")
                .artifactId("adventure-platform-bukkit")
                .version(Constants.Dependency.ADVENTURE_BUKKIT)
                .resolveTransitiveDependencies(true)
                .relocate(Relocation.builder()
                        .pattern("net{}kyori")
                        .relocatedPattern("me.whereareiam.socialismus.library")
                        .build())
                .build());

        addDependency(Library.builder()
                .groupId("net{}kyori")
                .artifactId("adventure-text-serializer-plain")
                .version(Constants.Dependency.ADVENTURE)
                .relocate(Relocation.builder()
                        .pattern("net{}kyori")
                        .relocatedPattern("me.whereareiam.socialismus.library")
                        .build())
                .build());

        addDependency(Library.builder()
                .groupId("net{}kyori")
                .artifactId("adventure-text-serializer-gson")
                .version(Constants.Dependency.ADVENTURE)
                .relocate(Relocation.builder()
                        .pattern("net{}kyori")
                        .relocatedPattern("me.whereareiam.socialismus.library")
                        .build())
                .build());

        addDependency(Library.builder()
                .groupId("net{}kyori")
                .artifactId("adventure-text-minimessage")
                .version(Constants.Dependency.ADVENTURE)
                .relocate(Relocation.builder()
                        .pattern("net{}kyori")
                        .relocatedPattern("me.whereareiam.socialismus.library")
                        .build())
                .build());

        addDependency(Library.builder()
                .groupId("org{}incendo")
                .artifactId("cloud-paper")
                .version(Constants.Dependency.CLOUD_PAPER)
                .resolveTransitiveDependencies(true)
                .build());
    }
}
