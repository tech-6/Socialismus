package me.whereareiam.socialismus.platform.velocity.inject;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.velocitypowered.api.plugin.PluginContainer;
import com.velocitypowered.api.proxy.ProxyServer;
import lombok.Getter;
import me.whereareiam.socialismus.adapter.config.ConfigBinder;
import me.whereareiam.socialismus.command.CommandModule;
import me.whereareiam.socialismus.common.CommonInjector;
import me.whereareiam.socialismus.common.CommonModule;
import me.whereareiam.socialismus.platform.velocity.VelocityDependencyResolver;

import java.nio.file.Path;

@Getter
public class VelocityInjector {
	public VelocityInjector(PluginContainer plugin, ProxyServer proxyServer, VelocityDependencyResolver dependencyResolver, Path dataPath) {
		Injector injector = Guice.createInjector(
				new VelocityInjectorConfiguration(plugin, proxyServer, dependencyResolver),
				new ConfigBinder(dataPath),
				new CommonModule(),
				new CommandModule()
		);

		CommonInjector.setInjector(injector);
	}
}
