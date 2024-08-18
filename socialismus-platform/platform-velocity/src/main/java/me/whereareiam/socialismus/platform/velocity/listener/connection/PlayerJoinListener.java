package me.whereareiam.socialismus.platform.velocity.listener.connection;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.velocitypowered.api.event.player.PlayerChooseInitialServerEvent;
import com.velocitypowered.api.proxy.Player;
import me.whereareiam.socialismus.api.input.container.PlayerContainerService;
import me.whereareiam.socialismus.api.model.player.DummyPlayer;
import me.whereareiam.socialismus.api.output.listener.DynamicListener;

@Singleton
public class PlayerJoinListener implements DynamicListener<PlayerChooseInitialServerEvent> {
    private final PlayerContainerService playerContainer;

    @Inject
    public PlayerJoinListener(PlayerContainerService playerContainer) {
        this.playerContainer = playerContainer;
    }

    public void onEvent(PlayerChooseInitialServerEvent event) {
        Player player = event.getPlayer();

        DummyPlayer dummyPlayer = DummyPlayer.builder()
                .username(player.getUsername())
                .uniqueId(player.getUniqueId())
                .audience(player)
                .location(event.getInitialServer().map(s -> s.getServerInfo().getName()).orElse(null))
                .locale(player.getEffectiveLocale())
                .build();

        playerContainer.addPlayer(dummyPlayer);
    }
}
