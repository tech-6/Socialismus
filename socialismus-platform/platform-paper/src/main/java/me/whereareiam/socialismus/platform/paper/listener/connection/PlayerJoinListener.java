package me.whereareiam.socialismus.platform.paper.listener.connection;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import me.whereareiam.socialismus.api.input.container.PlayerContainerService;
import me.whereareiam.socialismus.api.model.config.Settings;
import me.whereareiam.socialismus.api.model.player.DummyPlayer;
import me.whereareiam.socialismus.api.output.listener.DynamicListener;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;

@Singleton
public class PlayerJoinListener implements DynamicListener<PlayerJoinEvent> {
    private final Provider<Settings> settings;
    private final PlayerContainerService containerService;

    @Inject
    public PlayerJoinListener(Provider<Settings> settings, PlayerContainerService containerService) {
        this.settings = settings;
        this.containerService = containerService;
    }

    public void onEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (settings.get().getMisc().isDisableJoinNotification()) event.joinMessage(null);

        DummyPlayer dummyPlayer = DummyPlayer.builder()
                .username(player.getName())
                .uniqueId(player.getUniqueId())
                .audience(player)
                .location(player.getWorld().getName())
                .locale(player.locale())
                .build();

        containerService.addPlayer(dummyPlayer);
    }
}
