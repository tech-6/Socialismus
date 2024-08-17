package me.whereareiam.socialismus.platform.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.Dependency;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.PluginContainer;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import me.whereareiam.socialismus.common.CommonInjector;
import me.whereareiam.socialismus.common.CommonSocialismus;
import me.whereareiam.socialismus.common.Constants;
import me.whereareiam.socialismus.common.IntegrityChecker;
import me.whereareiam.socialismus.integration.bstats.bStatsIntegration;
import me.whereareiam.socialismus.integration.papiproxybridge.PAPIProxyBridgeIntegration;
import me.whereareiam.socialismus.integration.valiobungee.ValioBungeeIntegration;
import me.whereareiam.socialismus.platform.velocity.inject.VelocityInjector;
import org.bstats.velocity.Metrics;
import org.slf4j.Logger;

import java.nio.file.Path;

@Plugin(
        id = "socialismus",
        name = "@projectName@",
        version = "@projectVersion@",
        authors = "whereareiam",
        dependencies = {
                @Dependency(id = "packetevents", optional = true),
                @Dependency(id = "papiproxybridge", optional = true),
                @Dependency(id = "redisbungee", optional = true),
        }
)
public class VelocitySocialismus extends CommonSocialismus {
    private final ProxyServer proxyServer;
    private final PluginContainer pluginContainer;
    private final Logger logger;
    private final Metrics.Factory metricsFactory;
    private final Path dataPath;

    @Inject
    public VelocitySocialismus(ProxyServer proxyServer, PluginContainer pluginContainer, Logger logger, Metrics.Factory metricsFactory, @DataDirectory Path dataPath) {
        this.proxyServer = proxyServer;
        this.pluginContainer = pluginContainer;
        this.logger = logger;
        this.metricsFactory = metricsFactory;
        this.dataPath = dataPath;
    }

    @Subscribe
    public void onProxyInitializationEvent(ProxyInitializeEvent event) {
        VelocityDependencyResolver dependencyResolver = new VelocityDependencyResolver(this, logger, dataPath, proxyServer.getPluginManager());
        dependencyResolver.loadLibraries();
        dependencyResolver.resolveDependencies();

        new VelocityInjector(
                this,
                pluginContainer,
                proxyServer,
                dependencyResolver,
                metricsFactory.make(this, Constants.getBStatsVelocityId()),
                dataPath
        );

        VelocityLoggingHelper.setLogger(logger);

        if (CommonInjector.getInjector().getInstance(IntegrityChecker.class).checkIntegrity())
            throw new RuntimeException("Integrity check failed, plugin will be disabled");

        CommonInjector.getInjector().getInstance(PAPIProxyBridgeIntegration.class);
        CommonInjector.getInjector().getInstance(ValioBungeeIntegration.class);
        CommonInjector.getInjector().getInstance(bStatsIntegration.class);

        super.onEnable();
    }

    @Subscribe
    public void onProxyShutdownEvent(ProxyShutdownEvent event) {
        super.onDisable();
    }
}
