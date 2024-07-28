package me.whereareiam.socialismus.adapter.module;

import com.google.inject.AbstractModule;
import me.whereareiam.socialismus.api.output.module.ModuleService;

public class ModuleConfiguration extends AbstractModule {
    @Override
    protected void configure() {
        bind(ModuleService.class).to(ModuleManager.class);
    }
}
