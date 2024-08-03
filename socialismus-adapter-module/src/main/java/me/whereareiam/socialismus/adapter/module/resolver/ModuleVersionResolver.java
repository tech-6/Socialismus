package me.whereareiam.socialismus.adapter.module.resolver;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.whereareiam.socialismus.api.model.module.InternalModule;
import me.whereareiam.socialismus.api.output.LoggingHelper;
import me.whereareiam.socialismus.api.output.PlatformInteractor;

@Singleton
public class ModuleVersionResolver implements ModuleResolver {
    private final PlatformInteractor interactor;
    private final LoggingHelper loggingHelper;

    @Inject
    public ModuleVersionResolver(PlatformInteractor interactor, LoggingHelper loggingHelper) {
        this.interactor = interactor;
        this.loggingHelper = loggingHelper;
    }

    @Override
    public boolean resolve(InternalModule module) {
        boolean status = module.getSupportedVersions().contains(interactor.getServerVersion());

        if (!status)
            loggingHelper.warn("Module " + module.getName() + " does not support version " + interactor.getServerVersion());

        return status;
    }
}
