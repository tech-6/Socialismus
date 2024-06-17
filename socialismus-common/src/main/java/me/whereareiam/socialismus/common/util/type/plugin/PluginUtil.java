package me.whereareiam.socialismus.common.util.type.plugin;

import lombok.Getter;
import me.whereareiam.socialismus.common.util.type.AbstractTypeUtil;

public class PluginUtil extends AbstractTypeUtil {
	@Getter
	private static final boolean isBukkitPlugin = isClassPresent("me.whereareiam.socialismus.platform.bukkit.BukkitSocialismus");
	@Getter
	private static final boolean isPaperPlugin = isClassPresent("me.whereareiam.socialismus.platform.paper.PaperSocialismus");
	@Getter
	private static final boolean isVelocityPlugin = isClassPresent("me.whereareiam.socialismus.platform.velocity.VelocitySocialismus");
}

