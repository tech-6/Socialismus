package me.whereareiam.socialismus.platform.bukkit;

import com.google.inject.Singleton;
import me.whereareiam.socialismus.api.model.DummyPlayer;
import me.whereareiam.socialismus.api.output.PlatformInteractor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@Singleton
public class BukkitPlatformInteractor implements PlatformInteractor {
	@Override
	public boolean hasPermission(DummyPlayer dummyPlayer, String permission) {
		Player player = Bukkit.getPlayer(dummyPlayer.getUniqueId());
		if (player == null) return false;

		return player.hasPermission(permission);
	}
}
