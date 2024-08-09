package me.whereareiam.socialismus.platform.velocity;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import me.whereareiam.socialismus.api.model.player.DummyPlayer;
import me.whereareiam.socialismus.api.output.PlatformInteractor;
import me.whereareiam.socialismus.api.type.Version;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Singleton
public class VelocityPlatformInteractor implements PlatformInteractor {
    private final ProxyServer proxyServer;

    @Inject
    public VelocityPlatformInteractor(ProxyServer proxyServer) {
        this.proxyServer = proxyServer;
    }

    @Override
    public boolean hasPermission(DummyPlayer dummyPlayer, String permission) {
        return proxyServer
                .getPlayer(dummyPlayer.getUniqueId())
                .map(value -> value.hasPermission(permission))
                .orElse(false);
    }

    @Override
    public Version getServerVersion() {
        return Version.getLatest();
    }

    @Override
    public Optional<DummyPlayer> getDummyPlayer(UUID uniqueId) {
        Optional<Player> optionalPlayer = proxyServer.getPlayer(uniqueId);

        return optionalPlayer.map(player -> DummyPlayer.builder()
                .username(player.getUsername())
                .uniqueId(player.getUniqueId())
                .audience(player)
                .location(player.getCurrentServer().map(value -> value.getServerInfo().getName()).orElse(null))
                .locale(player.getEffectiveLocale())
                .build());

    }

    @Override
    public List<DummyPlayer> getPlayers(Set<UUID> uniqueIds) {
        return proxyServer.getAllPlayers().parallelStream()
                .filter(player -> uniqueIds.contains(player.getUniqueId()))
                .map(player -> DummyPlayer.builder()
                        .username(player.getUsername())
                        .uniqueId(player.getUniqueId())
                        .audience(player)
                        .location(player.getCurrentServer().map(value -> value.getServerInfo().getName()).orElse(null))
                        .locale(player.getEffectiveLocale())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public List<DummyPlayer> getOnlinePlayers() {
        return proxyServer.getAllPlayers().parallelStream()
                .map(player -> DummyPlayer.builder()
                        .username(player.getUsername())
                        .uniqueId(player.getUniqueId())
                        .audience(player)
                        .location(player.getCurrentServer().map(value -> value.getServerInfo().getName()).orElse(null))
                        .locale(player.getEffectiveLocale())
                        .build())
                .collect(Collectors.toList());
    }
}