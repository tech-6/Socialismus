package me.whereareiam.socialismus.paper;

import me.whereareiam.socialismus.core.SocialismusConfig;
import me.whereareiam.socialismus.core.platform.PlatformCommunicator;
import org.bukkit.plugin.Plugin;

public class SocialismusPaperConfig extends SocialismusConfig {
	public SocialismusPaperConfig(SocialismusPaper instance, Plugin plugin) {
		super(instance, plugin);
	}

	@Override
	protected void configurePlatformSpecifics() {
		bind(PlatformCommunicator.class).to(PaperCommunicator.class);
	}
}
