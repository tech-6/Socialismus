package me.whereareiam.socialismus.common.util.type.platform;

public enum PlatformType {
	BUKKIT,
	SPIGOT,
	PAPER,
	FOLIA,
	VELOCITY,
	UNKNOWN;

	public static PlatformType getType() {
		if (PlatformUtil.isVelocity())
			return VELOCITY;
		if (PlatformUtil.isFolia())
			return FOLIA;
		if (PlatformUtil.isPaper())
			return PAPER;
		if (PlatformUtil.isSpigot())
			return SPIGOT;
		if (PlatformUtil.isBukkit())
			return BUKKIT;

		return UNKNOWN;
	}
}
