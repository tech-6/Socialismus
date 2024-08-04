package me.whereareiam.socialismus.platform.velocity.inject;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.velocitypowered.api.event.EventManager;
import com.velocitypowered.api.plugin.PluginContainer;
import com.velocitypowered.api.proxy.ProxyServer;
import me.whereareiam.socialismus.api.input.DependencyResolver;
import me.whereareiam.socialismus.api.model.player.DummyPlayer;
import me.whereareiam.socialismus.api.output.*;
import me.whereareiam.socialismus.integration.bstats.Metrics;
import me.whereareiam.socialismus.integration.bstats.bStatsIntegration;
import me.whereareiam.socialismus.integration.papiproxybridge.PAPIProxyBridgeIntegration;
import me.whereareiam.socialismus.platform.velocity.*;
import me.whereareiam.socialismus.platform.velocity.listener.VelocityListenerRegistrar;
import org.incendo.cloud.CommandManager;

public class VelocityInjectorConfiguration extends AbstractModule {
    private final PluginContainer plugin;
    private final ProxyServer proxyServer;
    private final VelocityDependencyResolver dependencyResolver;
    private final org.bstats.velocity.Metrics metrics;

    public VelocityInjectorConfiguration(PluginContainer plugin, ProxyServer proxyServer, VelocityDependencyResolver dependencyResolver, org.bstats.velocity.Metrics metrics) {
        this.plugin = plugin;
        this.proxyServer = proxyServer;
        this.dependencyResolver = dependencyResolver;
        this.metrics = metrics;
    }

    @Override
    protected void configure() {
        bind(PluginContainer.class).toInstance(plugin);
        bind(ProxyServer.class).toInstance(proxyServer);
        bind(EventManager.class).toInstance(proxyServer.getEventManager());
        bind(DependencyResolver.class).toInstance(dependencyResolver);

        bind(LoggingHelper.class).to(VelocityLoggingHelper.class);
        bind(Scheduler.class).to(VelocityScheduler.class);
        bind(ListenerRegistrar.class).to(VelocityListenerRegistrar.class);
        bind(PlatformInteractor.class).to(VelocityPlatformInteractor.class);
        bind(PlatformClassLoader.class).to(VelocityClassLoader.class);
        bind(new TypeLiteral<CommandManager<DummyPlayer>>() {}).toProvider(VelocityCommandManagerProvider.class).asEagerSingleton();

        bind(org.bstats.velocity.Metrics.class).toInstance(metrics);
        bind(Metrics.class).to(VelocityMetrics.class);

        initIntegrations();
    }

    private void initIntegrations() {
        bind(bStatsIntegration.class).asEagerSingleton();
        bind(PAPIProxyBridgeIntegration.class).asEagerSingleton();
    }
}