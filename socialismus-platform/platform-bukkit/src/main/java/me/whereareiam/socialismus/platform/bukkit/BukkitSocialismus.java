package me.whereareiam.socialismus.platform.bukkit;

import me.whereareiam.socialismus.common.CommonInjector;
import me.whereareiam.socialismus.common.CommonSocialismus;
import me.whereareiam.socialismus.integration.bstats.bStatsIntegration;
import me.whereareiam.socialismus.integration.placeholderapi.PlaceholderAPIIntegration;
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

        CommonInjector.getInjector().getInstance(PlaceholderAPIIntegration.class).register();
        CommonInjector.getInjector().getInstance(bStatsIntegration.class).register();
    }

    @Override
    public void onDisable() {
        commonSocialismus.onDisable();
    }
}
