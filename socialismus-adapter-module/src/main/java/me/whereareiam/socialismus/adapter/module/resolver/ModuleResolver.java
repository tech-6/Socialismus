package me.whereareiam.socialismus.adapter.module.resolver;

import me.whereareiam.socialismus.api.model.module.InternalModule;

public interface ModuleResolver {
    boolean resolve(InternalModule module);
}
