package me.whereareiam.socialismus.platform.bukkit;

import me.whereareiam.socialismus.common.CommonSocialismus;
import me.whereareiam.socialismus.platform.bukkit.inject.BukkitInjector;
import org.bukkit.plugin.java.JavaPlugin;

import java.nio.file.Path;
import java.util.logging.Logger;

@SuppressWarnings("unused")
public class BukkitSocialismus extends JavaPlugin {
    private final CommonSocialismus commonSocialismus = new CommonSocialismus();
    private final Path dataPath = getDataFolder().toPath();
    private final Logger logger = getLogger();

    private BukkitDependencyResolver dependencyResolver;

    @Override
    public void onLoad() {
        dependencyResolver = new BukkitDependencyResolver(this);
        dependencyResolver.loadLibraries();
        dependencyResolver.resolveDependencies();
    }

    @Override
    public void onEnable() {
        new BukkitInjector(this, dependencyResolver, dataPath);
        BukkitLoggingHelper.setLogger(logger);

        commonSocialismus.onEnable();
    }

    @Override
    public void onDisable() {
        commonSocialismus.onDisable();
    }
}
