package me.whereareiam.socialismus.api.output;

import me.whereareiam.socialismus.api.model.DummyPlayer;

public interface PlatformInteractor {
	boolean hasPermission(DummyPlayer dummyPlayer, String permission);
}
