package me.whereareiam.socialismus.adapter.module.resolver;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.whereareiam.socialismus.api.PlatformType;
import me.whereareiam.socialismus.api.model.module.InternalModule;
import me.whereareiam.socialismus.api.output.LoggingHelper;

@Singleton
public class ModulePlatformResolver implements ModuleResolver {
    private final LoggingHelper loggingHelper;
    private final PlatformType platformType = PlatformType.getType();

    @Inject
    public ModulePlatformResolver(LoggingHelper loggingHelper) {
        this.loggingHelper = loggingHelper;
    }

    @Override
    public boolean resolve(InternalModule module) {
        boolean status = module.getSupportedPlatforms().contains(platformType);

        if (!status) loggingHelper.warn("Module " + module.getName() + " does not support platform " + platformType);

        return status;
    }
}
