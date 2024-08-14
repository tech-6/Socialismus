package me.whereareiam.socialismus.api.output;

import me.whereareiam.socialismus.api.model.player.DummyPlayer;
import me.whereareiam.socialismus.api.type.Version;

import java.util.UUID;

public interface PlatformInteractor {
    boolean areWithinRange(UUID player1, UUID player2, double range);

    boolean hasPermission(DummyPlayer dummyPlayer, String permission);

    int getOnlinePlayersCount();

    Version getServerVersion();
}
