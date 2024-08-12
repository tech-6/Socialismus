package me.whereareiam.socialismus.integration.papiproxybridge;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.whereareiam.socialismus.api.input.registry.Registry;
import me.whereareiam.socialismus.api.model.player.DummyPlayer;
import me.whereareiam.socialismus.api.output.integration.FormattingIntegration;
import me.whereareiam.socialismus.api.output.integration.Integration;
import net.william278.papiproxybridge.api.PlaceholderAPI;

@Singleton
public class PAPIProxyBridgeIntegration implements FormattingIntegration {
    private final Registry<Integration> registry;

    private PlaceholderAPI placeholderAPI;

    @Inject
    public PAPIProxyBridgeIntegration(Registry<Integration> registry) {
        this.registry = registry;
    }

    @Override
    public String format(DummyPlayer dummyPlayer, String content) {
        if (dummyPlayer.getUniqueId() == null) return content;

        return placeholderAPI.formatPlaceholders(content, dummyPlayer.getUniqueId()).getNow(content);
    }

    @Override
    public String getName() {
        return "PAPIProxyBridge";
    }

    @Override
    public boolean isAvailable() {
        try {
            Class.forName("net.william278.papiproxybridge.api.PlaceholderAPI");

            return true;
        } catch (ClassNotFoundException | NoClassDefFoundError e) {
            return false;
        }
    }

    @Override
    public void register() {
        if (!isAvailable()) return;

        placeholderAPI = PlaceholderAPI.createInstance();
        registry.register(this);
    }
}
