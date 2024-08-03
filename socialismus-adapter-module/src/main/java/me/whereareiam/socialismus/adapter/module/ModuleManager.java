package me.whereareiam.socialismus.adapter.module;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import lombok.Getter;
import lombok.Setter;
import me.whereareiam.socialismus.api.model.module.InternalModule;
import me.whereareiam.socialismus.api.model.module.Module;
import me.whereareiam.socialismus.api.output.LoggingHelper;
import me.whereareiam.socialismus.api.output.config.ConfigurationLoader;
import me.whereareiam.socialismus.api.output.module.ModuleService;
import me.whereareiam.socialismus.api.type.ModuleState;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Stream;

@Getter
@Setter
@Singleton
public class ModuleManager implements ModuleService {
    private static final String MODULE_FILE = "module.json";

    private final Path modulesPath;
    private final LoggingHelper loggingHelper;
    private final ConfigurationLoader configurationLoader;
    private final ModuleLifecycleController lifecycleController;

    private List<InternalModule> modules = new ArrayList<>();

    @Inject
    public ModuleManager(@Named("modulesPath") Path modulesPath, LoggingHelper loggingHelper, ConfigurationLoader configurationLoader,
                         ModuleLifecycleController moduleLifecycleController) {
        this.modulesPath = modulesPath;
        this.loggingHelper = loggingHelper;
        this.configurationLoader = configurationLoader;
        this.lifecycleController = moduleLifecycleController;
    }

    @Override
    public Optional<InternalModule> getModule(String name) {
        return modules.stream().filter(module -> module.getName().equals(name)).findFirst();
    }

    @Override
    public void loadModules() {
        discoverModules();
        modules.forEach(lifecycleController::loadModule);
        modules.forEach(lifecycleController::enableModule);
    }

    @Override
    public void unloadModules() {
        loggingHelper.info("Unloading modules...");
        modules.forEach(lifecycleController::disableModule);
        modules.forEach(lifecycleController::unloadModule);

        modules.removeIf(module -> !module.getState().equals(ModuleState.UNLOADED));
        modules.forEach(module -> loggingHelper.warn("Module was not unloaded: " + module.getName()));
    }

    @Override
    public void reloadModules() {
        unloadModules();
        loadModules();
    }

    private void discoverModules() {
        try (Stream<Path> paths = Files.list(modulesPath)) {
            List<File> moduleFiles = paths
                    .filter(Files::isRegularFile)
                    .filter(path -> path.getFileName().toString().endsWith(".jar"))
                    .map(Path::toFile)
                    .toList();

            moduleFiles.forEach(file -> {
                try (JarFile jarFile = new JarFile(file)) {
                    JarEntry entry = jarFile.getJarEntry(MODULE_FILE);
                    if (entry == null) {
                        loggingHelper.warn("Module file does not contain module.json file: " + file.getName());
                        return;
                    }

                    try (InputStream stream = jarFile.getInputStream(entry)) {
                        Module module = configurationLoader.load(stream, Module.class);
                        if (!validateModule(module, file)) {
                            return;
                        }

                        modules.add(InternalModule.builder()
                                .path(file.toPath())
                                .state(ModuleState.UNKNOWN)
                                .name(module.getName())
                                .version(module.getVersion())
                                .authors(module.getAuthors())
                                .supportedPlatforms(module.getSupportedPlatforms())
                                .supportedVersions(module.getSupportedVersions())
                                .dependencies(module.getDependencies())
                                .main(module.getMain())
                                .build());
                    }
                } catch (IOException e) {
                    loggingHelper.warn("Failed to load module from file: " + file.getName());
                }
            });
        } catch (IOException e) {
            loggingHelper.warn("Failed to load modules from directory: " + modulesPath);
        }
    }

    private boolean validateModule(Module module, File moduleJson) {
        if (module == null) {
            loggingHelper.warn("Failed to open module.json file: " + moduleJson.getName());
            return false;
        }

        if (module.getName() == null || module.getVersion() == null || module.getMain() == null) {
            loggingHelper.warn("Module file is missing required fields: " + moduleJson.getName());
            return false;
        }

        if (modules.stream().anyMatch(internalModule -> internalModule.getName().equals(module.getName()))) {
            loggingHelper.warn("Module with name already exists: " + module.getName());
            return false;
        }

        return true;
    }
}
