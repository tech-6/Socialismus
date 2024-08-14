package me.whereareiam.socialismus.api.input.container;

import me.whereareiam.socialismus.api.model.player.DummyPlayer;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface PlayerContainerService {
    void addPlayer(DummyPlayer dummyPlayer);

    void removePlayer(UUID uniqueId);

    void updatePlayer(UUID uniqueId, DummyPlayer dummyPlayer);

    boolean hasPlayer(UUID uniqueId);

    Optional<DummyPlayer> getPlayer(String username);

    Optional<DummyPlayer> getPlayer(UUID uniqueId);

    Set<DummyPlayer> getPlayers();
}
