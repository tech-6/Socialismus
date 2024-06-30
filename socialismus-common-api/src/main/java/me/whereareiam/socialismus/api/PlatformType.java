package me.whereareiam.socialismus.api;

public enum PlatformType {
	BUKKIT,
	SPIGOT,
	PAPER,
	FOLIA,
	VELOCITY,
	UNKNOWN;

	public static PlatformType getType() {
		if (isVelocity())
			return VELOCITY;
		if (isFolia())
			return FOLIA;
		if (isPaper())
			return PAPER;
		if (isSpigot())
			return SPIGOT;
		if (isBukkit())
			return BUKKIT;

		return UNKNOWN;
	}

	private static boolean isVelocity() {
		return isClassPresent("com.velocitypowered.api.plugin.Plugin");
	}

	private static boolean isFolia() {
		return isClassPresent("io.papermc.paper.threadedregions.ThreadedRegionizer");
	}

	private static boolean isPaper() {
		return isClassPresent("com.destroystokyo.paper.PaperConfig");
	}

	private static boolean isSpigot() {
		return isClassPresent("org.spigotmc.SpigotConfig");
	}

	private static boolean isBukkit() {
		return isClassPresent("org.bukkit.Bukkit");
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
