package me.whereareiam.socialismus.platform.paper.listener.connection;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import me.whereareiam.socialismus.api.input.container.PlayerContainerService;
import me.whereareiam.socialismus.api.model.config.Settings;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

@Singleton
public class PlayerQuitListener implements Listener {
    private final Provider<Settings> settings;
    private final PlayerContainerService containerService;

    @Inject
    public PlayerQuitListener(Provider<Settings> settings, PlayerContainerService containerService) {
        this.settings = settings;
        this.containerService = containerService;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoinEvent(PlayerQuitEvent event) {
        if (settings.get().getMisc().isDisableQuitNotification()) event.quitMessage(null);

        containerService.removePlayer(event.getPlayer().getUniqueId());
    }
}
