package me.whereareiam.socialismus.api.output;

import me.whereareiam.socialismus.api.model.player.DummyPlayer;
import me.whereareiam.socialismus.api.type.Version;

public interface PlatformInteractor {
    boolean hasPermission(DummyPlayer dummyPlayer, String permission);

    Version getServerVersion();
}
