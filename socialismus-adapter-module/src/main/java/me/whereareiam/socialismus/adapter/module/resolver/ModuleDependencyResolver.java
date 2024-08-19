package me.whereareiam.socialismus.adapter.module.resolver;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import me.whereareiam.socialismus.api.model.module.InternalModule;
import me.whereareiam.socialismus.api.model.module.ModuleDependency;
import me.whereareiam.socialismus.api.output.LoggingHelper;
import me.whereareiam.socialismus.api.output.integration.Integration;
import me.whereareiam.socialismus.api.output.module.ModuleService;
import me.whereareiam.socialismus.api.type.DependencyType;
import me.whereareiam.socialismus.api.type.ModuleState;
import me.whereareiam.socialismus.shared.Constants;

import java.util.Set;
import java.util.regex.Pattern;

@Singleton
public class ModuleDependencyResolver implements ModuleResolver {
    private final LoggingHelper loggingHelper;

    private final ModuleService moduleService;
    private final Provider<Set<Integration>> integrations;

    @Inject
    public ModuleDependencyResolver(LoggingHelper loggingHelper, ModuleService moduleService, Provider<Set<Integration>> integrations) {
        this.loggingHelper = loggingHelper;
        this.moduleService = moduleService;
        this.integrations = integrations;
    }

    @Override
    public boolean resolve(InternalModule module) {
        if (module.getDependencies().isEmpty()) return true;

        for (ModuleDependency dependency : module.getDependencies()) {
            DependencyType type = dependency.getType();
            String name = dependency.getName();
            String version = dependency.getVersion();

            switch (type) {
                case INTEGRATION:
                    if (!isIntegrationAvailable(name)) {
                        loggingHelper.warn("Module %s requires integration %s, which is not available", module.getName(), name);
                        return false;
                    }
                    break;
                case MODULE:
                    if (!isModuleAvailable(name, version)) {
                        loggingHelper.warn("Module %s requires module %s with version %s, which is not available", module.getName(), name, version);
                        return false;
                    }
                    break;
                case BASE:
                    if (!isBaseVersionCompatible(version)) {
                        loggingHelper.warn("Module %s requires version of the plugin to be %s, but it is %s", module.getName(), version, Constants.VERSION);
                        return false;
                    }
                    break;
            }
        }

        return true;
    }

    private boolean isIntegrationAvailable(String name) {
        return integrations.get().stream().anyMatch(integration -> integration.getName().equals(name));
    }

    private boolean isModuleAvailable(String name, String version) {
        Pattern versionPattern = Pattern.compile(version);
        return moduleService.getModules().stream()
                .anyMatch(module -> module.getName().equals(name) &&
                        versionPattern.matcher(module.getVersion()).matches() &&
                        module.getState() != ModuleState.ERROR);
    }

    private boolean isBaseVersionCompatible(String version) {
        Pattern versionPattern = Pattern.compile(version);

        return versionPattern.matcher(Constants.VERSION).matches();
    }
}