package me.whereareiam.socialismus.common;

import com.alessiodp.libby.Library;
import com.alessiodp.libby.relocation.Relocation;
import me.whereareiam.socialismus.api.input.DependencyResolver;

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
                .version(Constants.getCloudVersion())
                .resolveTransitiveDependencies(false)
                .build());

        addDependency(Library.builder()
                .groupId("org{}incendo")
                .artifactId("cloud-annotations")
                .version(Constants.getCloudVersion())
                .resolveTransitiveDependencies(false)
                .build());

        addDependency(Library.builder()
                .groupId("org{}incendo")
                .artifactId("cloud-minecraft-extras")
                .version(Constants.getCloudMinecraftExtrasVersion())
                .resolveTransitiveDependencies(false)
                .build());

        // Jackson libraries
        addDependency(Library.builder()
                .groupId("com{}fasterxml{}jackson{}core")
                .artifactId("jackson-databind")
                .version(Constants.getJacksonVersion())
                .resolveTransitiveDependencies(true)
                .build());

        addDependency(Library.builder()
                .groupId("com{}fasterxml{}jackson{}dataformat")
                .artifactId("jackson-dataformat-yaml")
                .version(Constants.getJacksonVersion())
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
