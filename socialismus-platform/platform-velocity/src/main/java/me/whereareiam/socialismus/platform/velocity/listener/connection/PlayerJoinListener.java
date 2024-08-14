package me.whereareiam.socialismus.platform.velocity.listener.connection;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.velocitypowered.api.event.PostOrder;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.LoginEvent;
import com.velocitypowered.api.proxy.Player;
import me.whereareiam.socialismus.api.input.container.PlayerContainerService;
import me.whereareiam.socialismus.api.model.player.DummyPlayer;

@Singleton
public class PlayerJoinListener {
    private final PlayerContainerService playerContainer;

    @Inject
    public PlayerJoinListener(PlayerContainerService playerContainer) {
        this.playerContainer = playerContainer;
    }

    @Subscribe(order = PostOrder.LAST)
    public void onPlayerChatEvent(LoginEvent event) {
        Player player = event.getPlayer();

        DummyPlayer dummyPlayer = DummyPlayer.builder()
                .username(player.getUsername())
                .uniqueId(player.getUniqueId())
                .audience(player)
                .location(player.getCurrentServer().isPresent()
                        ? player.getCurrentServer().get().getServerInfo().getName()
                        : null)
                .locale(player.getEffectiveLocale())
                .build();

        playerContainer.addPlayer(dummyPlayer);
    }
}
