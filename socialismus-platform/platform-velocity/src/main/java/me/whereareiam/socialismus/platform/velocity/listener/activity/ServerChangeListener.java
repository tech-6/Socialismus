package me.whereareiam.socialismus.platform.velocity.listener.activity;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.velocitypowered.api.event.PostOrder;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.ServerConnectedEvent;
import com.velocitypowered.api.proxy.ServerConnection;
import com.velocitypowered.api.proxy.server.ServerInfo;
import me.whereareiam.socialismus.api.input.container.PlayerContainerService;

@Singleton
public class ServerChangeListener {
    private final PlayerContainerService playerContainer;

    @Inject
    public ServerChangeListener(PlayerContainerService playerContainer) {
        this.playerContainer = playerContainer;
    }

    @Subscribe(order = PostOrder.LAST)
    public void onPlayerChatEvent(ServerConnectedEvent event) {
        playerContainer.getPlayer(event.getPlayer().getUniqueId()).ifPresent(
                player -> player.setLocation(event.getPlayer().getCurrentServer().map(ServerConnection::getServerInfo).map(ServerInfo::getName).orElse(null))
        );
    }
}