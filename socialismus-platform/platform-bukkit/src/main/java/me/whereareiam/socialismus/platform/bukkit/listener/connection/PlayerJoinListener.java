package me.whereareiam.socialismus.platform.bukkit.listener.connection;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import me.whereareiam.socialismus.api.input.container.PlayerContainerService;
import me.whereareiam.socialismus.api.model.config.Settings;
import me.whereareiam.socialismus.api.model.player.DummyPlayer;
import me.whereareiam.socialismus.api.output.listener.DynamicListener;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Locale;

@Singleton
public class PlayerJoinListener implements DynamicListener<PlayerJoinEvent> {
    private final Provider<Settings> settings;
    private final PlayerContainerService containerService;
    private final Provider<BukkitAudiences> audiencesProvider;

    @Inject
    public PlayerJoinListener(Provider<Settings> settings, PlayerContainerService containerService, Provider<BukkitAudiences> audiencesProvider) {
        this.settings = settings;
        this.containerService = containerService;
        this.audiencesProvider = audiencesProvider;
    }

    public void onEvent(PlayerJoinEvent event) {
        final BukkitAudiences audiences = audiencesProvider.get();
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
