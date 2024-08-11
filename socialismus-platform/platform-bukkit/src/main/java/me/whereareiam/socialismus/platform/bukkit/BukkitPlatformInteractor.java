package me.whereareiam.socialismus.platform.bukkit;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.whereareiam.socialismus.api.model.player.DummyPlayer;
import me.whereareiam.socialismus.api.output.PlatformInteractor;
import me.whereareiam.socialismus.api.type.Version;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.stream.Collectors;

@Singleton
public class BukkitPlatformInteractor implements PlatformInteractor {
    private final BukkitAudiences audiences;

    @Inject
    public BukkitPlatformInteractor(BukkitAudiences audiences) {
        this.audiences = audiences;
    }

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
    public Version getServerVersion() {
        return Version.of(Bukkit.getVersion());
    }

    @Override
    public Optional<DummyPlayer> getDummyPlayer(UUID uniqueId) {
        Player player = Bukkit.getPlayer(uniqueId);
        if (player == null) return Optional.empty();

        return Optional.of(DummyPlayer.builder()
                .username(player.getName())
                .uniqueId(player.getUniqueId())
                .audience(audiences.player(player))
                .location(player.getWorld().getName())
                .locale(Locale.of(player.getLocale()))
                .build());
    }

    @Override
    public List<DummyPlayer> getPlayers(Set<UUID> uniqueIds) {
        return Bukkit.getOnlinePlayers().parallelStream()
                .filter(player -> uniqueIds.contains(player.getUniqueId()))
                .map(player -> DummyPlayer.builder()
                        .username(player.getName())
                        .uniqueId(player.getUniqueId())
                        .audience(audiences.player(player))
                        .location(player.getWorld().getName())
                        .locale(Locale.of(player.getLocale()))
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public List<DummyPlayer> getOnlinePlayers() {
        return Bukkit.getOnlinePlayers().parallelStream()
                .map(player -> DummyPlayer.builder()
                        .username(player.getName())
                        .uniqueId(player.getUniqueId())
                        .audience(audiences.player(player))
                        .location(player.getWorld().getName())
                        .locale(Locale.of(player.getLocale()))
                        .build())
                .collect(Collectors.toList());
    }
}