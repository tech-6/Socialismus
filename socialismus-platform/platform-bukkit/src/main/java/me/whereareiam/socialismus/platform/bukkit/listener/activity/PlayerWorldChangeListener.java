package me.whereareiam.socialismus.platform.bukkit.listener.activity;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.whereareiam.socialismus.api.input.container.PlayerContainerService;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

@Singleton
public class PlayerWorldChangeListener implements Listener {
    private final PlayerContainerService playerContainer;

    @Inject
    public PlayerWorldChangeListener(PlayerContainerService playerContainer) {
        this.playerContainer = playerContainer;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoinEvent(PlayerJoinEvent event) {
        playerContainer.getPlayer(event.getPlayer().getUniqueId()).ifPresent(
                player -> player.setLocation(event.getPlayer().getWorld().getName())
        );
    }
}
