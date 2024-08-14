package me.whereareiam.socialismus.common.container;

import com.google.inject.Singleton;
import me.whereareiam.socialismus.api.input.container.PlayerContainerService;
import me.whereareiam.socialismus.api.model.player.DummyPlayer;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
public class PlayerContainer implements PlayerContainerService {
    private final ConcurrentHashMap<UUID, DummyPlayer> players = new ConcurrentHashMap<>();

    @Override
    public void addPlayer(DummyPlayer dummyPlayer) {
        players.put(dummyPlayer.getUniqueId(), dummyPlayer);
    }

    @Override
    public void removePlayer(UUID uniqueId) {
        players.remove(uniqueId);
    }

    @Override
    public void updatePlayer(UUID uniqueId, DummyPlayer dummyPlayer) {
        players.put(uniqueId, dummyPlayer);
    }

    @Override
    public boolean hasPlayer(UUID uniqueId) {
        return players.containsKey(uniqueId);
    }

    @Override
    public Optional<DummyPlayer> getPlayer(String username) {
        return players.values().stream().filter(player -> player.getUsername().equals(username)).findFirst();
    }

    @Override
    public Optional<DummyPlayer> getPlayer(UUID uniqueId) {
        return Optional.ofNullable(players.get(uniqueId));
    }

    @Override
    public Set<DummyPlayer> getPlayers() {
        return new HashSet<>(players.values());
    }
}
