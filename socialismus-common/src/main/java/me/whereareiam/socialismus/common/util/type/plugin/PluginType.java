package me.whereareiam.socialismus.common.util.type.plugin;

public enum PluginType {
	ALL,
	BUKKIT,
	PAPER,
	VELOCITY;

	public static PluginType getType() {
		if (PluginUtil.isBukkitPlugin() && PluginUtil.isPaperPlugin() && PluginUtil.isVelocityPlugin())
			return ALL;
		if (PluginUtil.isBukkitPlugin())
			return BUKKIT;
		if (PluginUtil.isPaperPlugin())
			return PAPER;
		if (PluginUtil.isVelocityPlugin())
			return VELOCITY;

		throw new IllegalStateException("Unknown plugin type");
	}
}
