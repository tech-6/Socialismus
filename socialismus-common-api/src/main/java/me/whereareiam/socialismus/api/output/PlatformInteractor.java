package me.whereareiam.socialismus.api.output;

import me.whereareiam.socialismus.api.model.player.DummyPlayer;
import me.whereareiam.socialismus.api.type.Version;
import net.kyori.adventure.text.Component;

import java.util.UUID;

public interface PlatformInteractor {
    void broadcast(Component component);

    boolean areWithinRange(UUID player1, UUID player2, double range);

    boolean hasPermission(String username, String permission);

    boolean hasPermission(DummyPlayer dummyPlayer, String permission);

    int getOnlinePlayersCount();

    Version getServerVersion();
}
