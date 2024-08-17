package me.whereareiam.socialismus.platform.bukkit.listener.connection;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import me.whereareiam.socialismus.api.input.container.PlayerContainerService;
import me.whereareiam.socialismus.api.model.player.DummyPlayer;
import me.whereareiam.socialismus.api.output.listener.DynamicListener;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerLoginEvent;

import java.util.Locale;

@Singleton
public class PlayerLoginListener implements DynamicListener<PlayerLoginEvent> {
    private final PlayerContainerService containerService;
    private final Provider<BukkitAudiences> audiences;

    @Inject
    public PlayerLoginListener(PlayerContainerService containerService, Provider<BukkitAudiences> audiences) {
        this.containerService = containerService;
        this.audiences = audiences;
    }

    public void onEvent(PlayerLoginEvent event) {
        Player player = event.getPlayer();

        DummyPlayer dummyPlayer = DummyPlayer.builder()
                .username(player.getName())
                .uniqueId(player.getUniqueId())
                .audience(audiences.get().player(player))
                .location(player.getWorld().getName())
                .locale(Locale.of(player.getLocale()))
                .build();

        containerService.addPlayer(dummyPlayer);
    }
}
