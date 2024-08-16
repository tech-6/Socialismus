package me.whereareiam.socialismus.platform.velocity.listener.connection;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.velocitypowered.api.event.connection.LoginEvent;
import com.velocitypowered.api.proxy.Player;
import me.whereareiam.socialismus.api.input.container.PlayerContainerService;
import me.whereareiam.socialismus.api.model.player.DummyPlayer;
import me.whereareiam.socialismus.api.output.listener.DynamicListener;

@Singleton
public class PlayerJoinListener implements DynamicListener<LoginEvent> {
    private final PlayerContainerService playerContainer;

    @Inject
    public PlayerJoinListener(PlayerContainerService playerContainer) {
        this.playerContainer = playerContainer;
    }

    public void onEvent(LoginEvent event) {
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
