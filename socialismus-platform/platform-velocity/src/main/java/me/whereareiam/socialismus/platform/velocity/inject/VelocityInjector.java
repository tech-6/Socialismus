package me.whereareiam.socialismus.platform.velocity.inject;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.velocitypowered.api.proxy.ProxyServer;
import lombok.Getter;
import me.whereareiam.socialismus.adapter.config.ConfigBinder;
import me.whereareiam.socialismus.common.CommonInjector;
import me.whereareiam.socialismus.platform.velocity.VelocityDependencyResolver;

import java.nio.file.Path;

@Getter
public class VelocityInjector {
	public VelocityInjector(ProxyServer proxyServer, VelocityDependencyResolver dependencyResolver, Path dataPath) {
		Injector injector = Guice.createInjector(
				new VelocityInjectorConfiguration(proxyServer, dependencyResolver),
				new ConfigBinder(dataPath)
		);

		CommonInjector.setInjector(injector);
	}
}
