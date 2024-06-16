package me.whereareiam.socialismus.platform.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import me.whereareiam.socialismus.common.SocialismusBase;

import java.nio.file.Path;
import java.util.logging.Logger;

@Plugin(
		id = "socialismus",
		name = "@projectName@",
		version = "@projectVersion@",
		authors = "whereareiam"
)
@SuppressWarnings("unused")
public class VelocitySocialismus extends SocialismusBase {
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
		super.onEnable();

		if (!dataPath.toFile().exists()) {
			if (!dataPath.toFile().mkdir()) {
				logger.severe("Failed to create data folder. Disabling proxy.");
				proxyServer.shutdown();
			}
		}

		new VelocityInjector(dataPath);
	}

	@Subscribe
	public void onProxyShutdownEvent(ProxyShutdownEvent event) {
		super.onDisable();

	}
}
