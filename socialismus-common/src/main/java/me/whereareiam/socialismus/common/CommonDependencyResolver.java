package me.whereareiam.socialismus.common;

import com.alessiodp.libby.Library;
import com.alessiodp.libby.relocation.Relocation;
import me.whereareiam.socialismus.api.input.DependencyResolver;
import me.whereareiam.socialismus.shared.Constants;

import java.util.ArrayList;
import java.util.List;

public abstract class CommonDependencyResolver implements DependencyResolver {
    protected final List<Library> libraries = new ArrayList<>();

    @Override
    public void loadLibraries() {
        // Cloud libraries
        addDependency(Library.builder()
                .groupId("org{}incendo")
                .artifactId("cloud-core")
                .version(Constants.Dependency.CLOUD)
                .resolveTransitiveDependencies(false)
                .build());

        addDependency(Library.builder()
                .groupId("org{}incendo")
                .artifactId("cloud-annotations")
                .version(Constants.Dependency.CLOUD)
                .resolveTransitiveDependencies(false)
                .build());

        addDependency(Library.builder()
                .groupId("org{}incendo")
                .artifactId("cloud-minecraft-extras")
                .version(Constants.Dependency.CLOUD_MINECRAFT_EXTRAS)
                .resolveTransitiveDependencies(false)
                .build());

        // Jackson libraries
        addDependency(Library.builder()
                .groupId("com{}fasterxml{}jackson{}core")
                .artifactId("jackson-databind")
                .version(Constants.Dependency.JACKSON)
                .resolveTransitiveDependencies(true)
                .build());

        addDependency(Library.builder()
                .groupId("com{}fasterxml{}jackson{}dataformat")
                .artifactId("jackson-dataformat-yaml")
                .version(Constants.Dependency.JACKSON)
                .resolveTransitiveDependencies(true)
                .relocate(
                        Relocation.builder()
                                .pattern("org{}yaml{}snakeyaml")
                                .relocatedPattern("me.whereareiam.socialismus.library.snakeyaml")
                                .build()
                ).build());
    }

    @Override
    public void addDependency(Library library) {
        libraries.add(library);
    }

    @Override
    public void clearDependencies() {
        libraries.clear();
    }
}
