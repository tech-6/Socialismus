package me.whereareiam.socialismus.adapter.module.resolver;

import com.google.inject.Singleton;
import me.whereareiam.socialismus.api.model.module.InternalModule;

@Singleton
public class ModuleDependencyResolver implements ModuleResolver {
    @Override
    public boolean resolve(InternalModule module) {
        if (module.getDependencies().isEmpty()) return true;

        return false;
    }
}
