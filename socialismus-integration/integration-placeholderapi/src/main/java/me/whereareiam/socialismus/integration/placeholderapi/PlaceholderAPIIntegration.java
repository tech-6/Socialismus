package me.whereareiam.socialismus.integration.placeholderapi;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.clip.placeholderapi.PlaceholderAPI;
import me.whereareiam.socialismus.api.input.registry.Registry;
import me.whereareiam.socialismus.api.model.player.DummyPlayer;
import me.whereareiam.socialismus.api.output.integration.FormattingIntegration;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

@Singleton
public class PlaceholderAPIIntegration implements FormattingIntegration {
    private final Registry<FormattingIntegration> registry;

    @Inject
    public PlaceholderAPIIntegration(Registry<FormattingIntegration> registry) {this.registry = registry;}

    @Override
    public String format(DummyPlayer dummyPlayer, String content) {
        OfflinePlayer player = Bukkit.getOfflinePlayer(dummyPlayer.getUniqueId());

        return PlaceholderAPI.setPlaceholders(player, content);
    }

    @Override
    public boolean isAvailable() {
        try {
            Class.forName("me.clip.placeholderapi.PlaceholderAPI");
            registry.register(this);

            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }
}
