package me.whereareiam.socialismus.api.output;

import me.whereareiam.socialismus.api.model.player.DummyPlayer;
import me.whereareiam.socialismus.api.type.Version;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface PlatformInteractor {
    boolean areWithinRange(UUID player1, UUID player2, double range);

    boolean hasPermission(DummyPlayer dummyPlayer, String permission);

    Version getServerVersion();

    Optional<DummyPlayer> getDummyPlayer(UUID uniqueId);

    List<DummyPlayer> getPlayers(Set<UUID> uniqueIds);

    List<DummyPlayer> getOnlinePlayers();
}
