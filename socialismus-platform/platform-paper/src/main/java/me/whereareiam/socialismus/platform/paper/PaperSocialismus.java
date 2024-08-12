package me.whereareiam.socialismus.platform.paper;

import me.whereareiam.socialismus.common.CommonInjector;
import me.whereareiam.socialismus.common.CommonSocialismus;
import me.whereareiam.socialismus.integration.bstats.bStatsIntegration;
import me.whereareiam.socialismus.platform.paper.inject.PaperInjector;
import org.bukkit.plugin.java.JavaPlugin;

import java.nio.file.Path;
import java.util.logging.Logger;

@SuppressWarnings("unused")
public class PaperSocialismus extends JavaPlugin {
    private final CommonSocialismus commonSocialismus = new CommonSocialismus();
    private final Path dataPath = getDataFolder().toPath();
    private final Logger logger = getLogger();

    private PaperDependencyResolver dependencyResolver;

    @Override
    public void onLoad() {
        dependencyResolver = new PaperDependencyResolver(this);
        dependencyResolver.loadLibraries();
        dependencyResolver.resolveDependencies();
    }

    @Override
    public void onEnable() {
        new PaperInjector(this, dependencyResolver, dataPath);
        PaperLoggingHelper.setLogger(logger);

        commonSocialismus.onEnable();

        CommonInjector.getInjector().getInstance(bStatsIntegration.class).register();
    }

    @Override
    public void onDisable() {
        commonSocialismus.onDisable();
    }
}
