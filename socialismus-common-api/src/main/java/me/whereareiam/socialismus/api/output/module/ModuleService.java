package me.whereareiam.socialismus.api.output.module;

import me.whereareiam.socialismus.api.model.module.InternalModule;

import java.util.List;
import java.util.Optional;

public interface ModuleService {
    void loadModules();

    void unloadModules();

    void reloadModules();

    List<InternalModule> getModules();

    Optional<InternalModule> getModule(String name);
}
