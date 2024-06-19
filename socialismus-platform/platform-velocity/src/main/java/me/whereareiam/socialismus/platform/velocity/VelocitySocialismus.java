package me.whereareiam.socialismus.platform.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import me.whereareiam.socialismus.common.CommonSocialismus;
import me.whereareiam.socialismus.platform.velocity.inject.VelocityInjector;
import org.slf4j.Logger;

import java.nio.file.Path;

@Plugin(
		id = "socialismus",
		name = "@projectName@",
		version = "@projectVersion@",
		authors = "whereareiam"
)
@SuppressWarnings("unused")
public class VelocitySocialismus extends CommonSocialismus {
	private final ProxyServer proxyServer;
	private final Logger logger;
	private final Path dataPath;

	@Inject
	public VelocitySocialismus(ProxyServer proxyServer, Logger logger, @DataDirectory Path dataPath) {
		this.proxyServer = proxyServer;
		this.logger = logger;
		this.dataPath = dataPath;
	}

	@Subscribe
	public void onProxyInitializationEvent(ProxyInitializeEvent event) {
		VelocityDependencyResolver dependencyResolver = new VelocityDependencyResolver(proxyServer, logger, dataPath, proxyServer.getPluginManager());
		dependencyResolver.loadLibraries();
		dependencyResolver.resolveDependencies();

		new VelocityInjector(proxyServer, dependencyResolver, dataPath);
		VelocityLoggingHelper.setLogger(logger);

		super.onEnable();
	}

	@Subscribe
	public void onProxyShutdownEvent(ProxyShutdownEvent event) {
		super.onDisable();

	}
}
