package me.whereareiam.socialismus.platform.velocity;

import com.alessiodp.libby.Library;
import com.alessiodp.libby.VelocityLibraryManager;
import com.velocitypowered.api.plugin.PluginManager;
import me.whereareiam.socialismus.common.CommonDependencyResolver;
import me.whereareiam.socialismus.shared.Constants;
import org.slf4j.Logger;

import java.nio.file.Path;

@SuppressWarnings({"rawtypes", "unchecked"})
public class VelocityDependencyResolver extends CommonDependencyResolver {
    private final VelocityLibraryManager libraryManager;

    public VelocityDependencyResolver(VelocitySocialismus velocitySocialismus, Logger logger, Path dataPath, PluginManager pluginManager) {
        this.libraryManager = new VelocityLibraryManager(velocitySocialismus, logger, dataPath, pluginManager, ".libraries");
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

        // Velocity specific libraries
        addDependency(Library.builder()
                .groupId("org{}incendo")
                .artifactId("cloud-velocity")
                .version(Constants.Dependency.CLOUD_VELOCITY)
                .resolveTransitiveDependencies(true)
                .build());
    }
}
