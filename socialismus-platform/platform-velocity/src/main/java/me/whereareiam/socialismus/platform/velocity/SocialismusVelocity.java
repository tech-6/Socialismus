package me.whereareiam.socialismus.platform.velocity;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.Plugin;
import me.whereareiam.socialismus.platform.SocialismusBase;

@Plugin(
		id = "socialismus",
		name = "@projectName@",
		version = "@projectVersion@",
		authors = "whereareiam"
)
@SuppressWarnings("unused")
public class SocialismusVelocity extends SocialismusBase {
	@Subscribe
	public void onProxyInitializationEvent(ProxyInitializeEvent event) {
		super.onEnable();

	}

	@Subscribe
	public void onProxyShutdownEvent(ProxyShutdownEvent event) {
		super.onDisable();

	}
}
