package me.whereareiam.socialismus.api.input;

import com.alessiodp.libby.Library;

public interface DependencyResolver {
    void resolveDependencies();

    void loadLibraries();

    void addDependency(Library library);

    void clearDependencies();
}
