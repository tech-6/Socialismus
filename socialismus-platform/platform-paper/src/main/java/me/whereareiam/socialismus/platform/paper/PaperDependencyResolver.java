package me.whereareiam.socialismus.platform.paper;

import com.alessiodp.libby.Library;
import com.alessiodp.libby.PaperLibraryManager;
import com.alessiodp.libby.relocation.Relocation;
import me.whereareiam.socialismus.common.CommonDependencyResolver;
import me.whereareiam.socialismus.common.Constants;
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
        addDependency(Library.builder()
                .groupId("com{}google{}inject")
                .artifactId("guice")
                .version(Constants.getGuiceVersion())
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
                .groupId("org{}incendo")
                .artifactId("cloud-paper")
                .version(Constants.getCloudPaperVersion())
                .resolveTransitiveDependencies(true)
                .build());
    }
}
