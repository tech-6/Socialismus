package me.whereareiam.socialismus.platform.paper;

import me.whereareiam.socialismus.common.CommonInjector;
import me.whereareiam.socialismus.common.CommonSocialismus;
import me.whereareiam.socialismus.common.IntegrityChecker;
import me.whereareiam.socialismus.integration.bstats.bStatsIntegration;
import me.whereareiam.socialismus.integration.placeholderapi.PlaceholderAPIIntegration;
import me.whereareiam.socialismus.platform.paper.inject.PaperInjector;
import org.bukkit.plugin.java.JavaPlugin;

import java.nio.file.Path;
import java.util.logging.Logger;

@SuppressWarnings("unused")
public class PaperSocialismus extends JavaPlugin {
    private final CommonSocialismus commonSocialismus = new CommonSocialismus();
    private final Path dataPath = getDataFolder().toPath();
    private final Logger logger = getLogger();

    @Override
    public void onLoad() {
        PaperDependencyResolver dependencyResolver = new PaperDependencyResolver(this);
        dependencyResolver.loadLibraries();
        dependencyResolver.resolveDependencies();

        new PaperInjector(this, dependencyResolver, dataPath);
        PaperLoggingHelper.setLogger(logger);

        if (CommonInjector.getInjector().getInstance(IntegrityChecker.class).checkIntegrity())
            getServer().getPluginManager().disablePlugin(this);
    }

    @Override
    public void onEnable() {
        commonSocialismus.onEnable();

        CommonInjector.getInjector().getInstance(PlaceholderAPIIntegration.class).register();
        CommonInjector.getInjector().getInstance(bStatsIntegration.class).register();
    }

    @Override
    public void onDisable() {
        commonSocialismus.onDisable();
    }
}
