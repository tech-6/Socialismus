package me.whereareiam.socialismus.api;

public enum PluginType {
	ALL, BUKKIT, PAPER, VELOCITY;

	public static PluginType getType() {
		if (isBukkitPlugin() && isPaperPlugin() && isVelocityPlugin()) return ALL;
		if (isBukkitPlugin()) return BUKKIT;
		if (isPaperPlugin()) return PAPER;
		if (isVelocityPlugin()) return VELOCITY;

		throw new IllegalStateException("Unknown plugin type");
	}

	private static boolean isBukkitPlugin() {
		return isClassPresent("me.whereareiam.socialismus.platform.bukkit.BukkitSocialismus");
	}

	private static boolean isPaperPlugin() {
		return isClassPresent("me.whereareiam.socialismus.platform.paper.PaperSocialismus");
	}

	private static boolean isVelocityPlugin() {
		return isClassPresent("me.whereareiam.socialismus.platform.velocity.VelocitySocialismus");
	}

	private static boolean isClassPresent(String className) {
		try {
			Class.forName(className);
			return true;
		} catch (ClassNotFoundException e) {
			return false;
		} catch (NoClassDefFoundError e) {
			return className.equals("org.bukkit.plugin.java.JavaPlugin");
		}
	}
}
