package me.whereareiam.socialismus.common.util.type.platform;

import lombok.Getter;
import me.whereareiam.socialismus.common.util.type.AbstractTypeUtil;

public class PlatformUtil extends AbstractTypeUtil {
	@Getter
	private static final boolean isVelocity = isClassPresent("com.velocitypowered.api.plugin.Plugin");
	@Getter
	private static final boolean isFolia = isClassPresent("io.papermc.paper.threadedregions.ThreadedRegionizer");
	@Getter
	private static final boolean isPaper = isClassPresent("com.destroystokyo.paper.PaperConfig");
	@Getter
	private static final boolean isSpigot = isClassPresent("org.spigotmc.SpigotConfig");
	@Getter
	private static final boolean isBukkit = isClassPresent("org.bukkit.Bukkit");
}
