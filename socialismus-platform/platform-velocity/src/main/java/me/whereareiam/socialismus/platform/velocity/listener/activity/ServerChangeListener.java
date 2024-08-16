package me.whereareiam.socialismus.platform.velocity.listener.activity;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.velocitypowered.api.event.player.ServerConnectedEvent;
import com.velocitypowered.api.proxy.ServerConnection;
import com.velocitypowered.api.proxy.server.ServerInfo;
import me.whereareiam.socialismus.api.input.container.PlayerContainerService;
import me.whereareiam.socialismus.api.output.listener.DynamicListener;

@Singleton
public class ServerChangeListener implements DynamicListener<ServerConnectedEvent> {
    private final PlayerContainerService playerContainer;

    @Inject
    public ServerChangeListener(PlayerContainerService playerContainer) {
        this.playerContainer = playerContainer;
    }

    public void onEvent(ServerConnectedEvent event) {
        playerContainer.getPlayer(event.getPlayer().getUniqueId()).ifPresent(
                player -> player.setLocation(event.getPlayer().getCurrentServer().map(ServerConnection::getServerInfo).map(ServerInfo::getName).orElse(null))
        );
    }
}