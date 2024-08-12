package me.whereareiam.socialismus.platform.paper;

import com.google.inject.Singleton;
import me.whereareiam.socialismus.api.model.player.DummyPlayer;
import me.whereareiam.socialismus.api.output.PlatformInteractor;
import me.whereareiam.socialismus.api.type.Version;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Singleton
public class PaperPlatformInteractor implements PlatformInteractor {
    @Override
    public boolean areWithinRange(UUID player1, UUID player2, double range) {
        Player p1 = Bukkit.getPlayer(player1);
        Player p2 = Bukkit.getPlayer(player2);
        if (p1 == null || p2 == null) return false;

        return p1.getLocation().distanceSquared(p2.getLocation()) <= range * range;
    }

    @Override
    public boolean hasPermission(DummyPlayer dummyPlayer, String permission) {
        Player player = Bukkit.getPlayer(dummyPlayer.getUniqueId());
        if (player == null) return false;

        return player.hasPermission(permission);
    }

    @Override
    public int getOnlinePlayersCount() {
        return Bukkit.getOnlinePlayers().size();
    }

    @Override
    public Version getServerVersion() {
        return Version.of(Bukkit.getVersion());
    }

    @Override
    public Optional<DummyPlayer> getDummyPlayer(UUID uniqueId) {
        Player player = Bukkit.getPlayer(uniqueId);
        if (player == null) return Optional.empty();

        return Optional.of(
                DummyPlayer.builder()
                        .username(player.getName())
                        .uniqueId(player.getUniqueId())
                        .audience(null)
                        .location(player.getWorld().getName())
                        .locale(player.locale())
                        .build()
        );
    }

    @Override
    public List<DummyPlayer> getPlayers(Set<UUID> uniqueIds) {
        return Bukkit.getOnlinePlayers().parallelStream()
                .filter(player -> uniqueIds.contains(player.getUniqueId()))
                .map(player -> DummyPlayer.builder()
                        .username(player.getName())
                        .uniqueId(player.getUniqueId())
                        .audience(null)
                        .location(player.getWorld().getName())
                        .locale(player.locale())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public List<DummyPlayer> getOnlinePlayers() {
        return Bukkit.getOnlinePlayers().parallelStream()
                .map(player -> DummyPlayer.builder()
                        .username(player.getName())
                        .uniqueId(player.getUniqueId())
                        .audience(null)
                        .location(player.getWorld().getName())
                        .locale(player.locale())
                        .build())
                .collect(Collectors.toList());
    }
}
