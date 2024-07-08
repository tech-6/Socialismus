package me.whereareiam.socialismus.platform.paper;

import com.google.inject.Singleton;
import me.whereareiam.socialismus.api.model.player.DummyPlayer;
import me.whereareiam.socialismus.api.output.PlatformInteractor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@Singleton
public class PaperPlatformInteractor implements PlatformInteractor {
	@Override
	public boolean hasPermission(DummyPlayer dummyPlayer, String permission) {
		Player player = Bukkit.getPlayer(dummyPlayer.getUniqueId());
		if (player == null) return false;

		return player.hasPermission(permission);
	}

	@Override
	public String getServerVersion() {
		return Bukkit.getVersion().split(" ")[0];
	}
}
