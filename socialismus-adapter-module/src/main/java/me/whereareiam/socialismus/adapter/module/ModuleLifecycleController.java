package me.whereareiam.socialismus.adapter.module;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import me.whereareiam.socialismus.api.model.module.InternalModule;
import me.whereareiam.socialismus.api.output.LoggingHelper;
import me.whereareiam.socialismus.api.output.PlatformClassLoader;
import me.whereareiam.socialismus.api.output.module.SocialisticModule;
import me.whereareiam.socialismus.api.type.ModuleState;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

@Singleton
public class ModuleLifecycleController {
    private final Injector injector;
    private final LoggingHelper loggingHelper;
    private final PlatformClassLoader platformClassLoader;

    @Inject
    public ModuleLifecycleController(Injector injector, LoggingHelper loggingHelper, PlatformClassLoader platformClassLoader) {
        this.injector = injector;
        this.loggingHelper = loggingHelper;
        this.platformClassLoader = platformClassLoader;
    }

    public void loadModule(InternalModule module) {
        if (!module.getState().equals(ModuleState.UNKNOWN)) return;

        try {
            URLClassLoader loader = new URLClassLoader(new URL[]{module.getPath().toUri().toURL()}, platformClassLoader.getClassLoader());
            Class<?> moduleClass = Class.forName(module.getMain(), true, loader);

            module.setModule((SocialisticModule) injector.getInstance(moduleClass));
            injector.injectMembers(module.getModule());

            module.setState(ModuleState.LOADED);

            // TODO Check supportedPlatforms, supportedVersions, dependencies

            module.getModule().onLoad();

            loggingHelper.info("Loaded module " + module.getName() + " v" + module.getVersion() + " [" + String.join(", ", module.getAuthors()) + "]");
        } catch (MalformedURLException | ClassNotFoundException e) {
            loggingHelper.severe("Failed to load module " + module.getName() + ": " + e);
            module.setState(ModuleState.ERROR);
        }
    }

    public void enableModule(InternalModule module) {
        if (!module.getState().equals(ModuleState.LOADED)) return;

    }

    public void disableModule(InternalModule module) {
        if (!module.getState().equals(ModuleState.ENABLED)) return;

    }

    public void unloadModule(InternalModule module) {
        if (!module.getState().equals(ModuleState.DISABLED)) return;

    }
}
