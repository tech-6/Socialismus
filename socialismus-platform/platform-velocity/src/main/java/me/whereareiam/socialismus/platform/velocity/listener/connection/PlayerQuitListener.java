package me.whereareiam.socialismus.platform.velocity.listener.connection;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.velocitypowered.api.event.PostOrder;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.DisconnectEvent;
import me.whereareiam.socialismus.api.input.container.PlayerContainerService;

@Singleton
public class PlayerQuitListener {
    private final PlayerContainerService playerContainer;

    @Inject
    public PlayerQuitListener(PlayerContainerService playerContainer) {
        this.playerContainer = playerContainer;
    }

    @Subscribe(order = PostOrder.LAST)
    public void onPlayerChatEvent(DisconnectEvent event) {
        playerContainer.removePlayer(event.getPlayer().getUniqueId());
    }
}
