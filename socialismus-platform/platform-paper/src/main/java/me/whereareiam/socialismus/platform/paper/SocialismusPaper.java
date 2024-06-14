package me.whereareiam.socialismus.platform.paper;

import me.whereareiam.socialismus.platform.SocialismusBase;
import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings("unused")
public class SocialismusPaper extends JavaPlugin {
	private final SocialismusBase socialismusBase = new SocialismusBase();

	@Override
	public void onEnable() {
		socialismusBase.onEnable();
	}

	@Override
	public void onDisable() {
		socialismusBase.onDisable();
	}
}
