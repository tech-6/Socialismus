package me.whereareiam.socialismus.platform.bukkit.listener.connection;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import me.whereareiam.socialismus.api.input.container.PlayerContainerService;
import me.whereareiam.socialismus.api.model.config.Settings;
import me.whereareiam.socialismus.api.model.player.DummyPlayer;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Locale;

@Singleton
public class PlayerJoinListener implements Listener {
    private final Provider<Settings> settings;
    private final PlayerContainerService containerService;
    private final BukkitAudiences audiences;

    @Inject
    public PlayerJoinListener(Provider<Settings> settings, PlayerContainerService containerService, BukkitAudiences audiences) {
        this.settings = settings;
        this.containerService = containerService;
        this.audiences = audiences;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoinEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (settings.get().getMisc().isDisableJoinNotification()) event.setJoinMessage(null);

        DummyPlayer dummyPlayer = DummyPlayer.builder()
                .username(player.getName())
                .uniqueId(player.getUniqueId())
                .audience(audiences.player(event.getPlayer()))
                .location(player.getWorld().getName())
                .locale(Locale.of(player.getLocale()))
                .build();

        containerService.addPlayer(dummyPlayer);
    }
}
