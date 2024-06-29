package me.whereareiam.socialismus.api.output.platform;

import me.whereareiam.socialismus.api.model.player.DummyPlayer;

public interface PlatformInteractor {
	boolean hasPermission(DummyPlayer dummyPlayer, String permission);
}
