package me.whereareiam.socialismus.integration.placeholderapi;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.clip.placeholderapi.PlaceholderAPI;
import me.whereareiam.socialismus.api.input.registry.Registry;
import me.whereareiam.socialismus.api.model.player.DummyPlayer;
import me.whereareiam.socialismus.api.output.integration.FormattingIntegration;
import me.whereareiam.socialismus.api.output.integration.Integration;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

@Singleton
public class PlaceholderAPIIntegration implements FormattingIntegration {
    private final Registry<Integration> registry;

    @Inject
    public PlaceholderAPIIntegration(Registry<Integration> registry) {
        this.registry = registry;
    }

    @Override
    public String format(DummyPlayer dummyPlayer, String content) {
        if (dummyPlayer.getUniqueId() == null) return content;

        OfflinePlayer player = Bukkit.getOfflinePlayer(dummyPlayer.getUniqueId());

        return PlaceholderAPI.setPlaceholders(player, content);
    }

    @Override
    public String getName() {
        return "PlaceholderAPI";
    }

    @Override
    public boolean isAvailable() {
        try {
            Class.forName("me.clip.placeholderapi.PlaceholderAPI");

            return true;
        } catch (ClassNotFoundException | NoClassDefFoundError e) {
            return false;
        }
    }

    @Override
    public void register() {
        if (!isAvailable()) return;

        registry.register(this);
    }
}
