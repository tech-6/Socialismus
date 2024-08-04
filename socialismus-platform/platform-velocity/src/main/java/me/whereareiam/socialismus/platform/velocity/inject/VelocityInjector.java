package me.whereareiam.socialismus.platform.velocity.inject;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.velocitypowered.api.plugin.PluginContainer;
import com.velocitypowered.api.proxy.ProxyServer;
import lombok.Getter;
import me.whereareiam.socialismus.adapter.config.ConfigBinder;
import me.whereareiam.socialismus.adapter.module.ModuleConfiguration;
import me.whereareiam.socialismus.command.CommandConfiguration;
import me.whereareiam.socialismus.common.CommonConfiguration;
import me.whereareiam.socialismus.common.CommonInjector;
import me.whereareiam.socialismus.platform.velocity.VelocityDependencyResolver;
import org.bstats.velocity.Metrics;

import java.nio.file.Path;

@Getter
public class VelocityInjector {
    public VelocityInjector(PluginContainer plugin, ProxyServer proxyServer, VelocityDependencyResolver dependencyResolver, Metrics metrics, Path dataPath) {
        Injector injector = Guice.createInjector(
                new VelocityInjectorConfiguration(plugin, proxyServer, dependencyResolver, metrics),
                new ConfigBinder(dataPath),
                new CommonConfiguration(),
                new CommandConfiguration(),
                new ModuleConfiguration()
        );

        CommonInjector.setInjector(injector);
    }
}
