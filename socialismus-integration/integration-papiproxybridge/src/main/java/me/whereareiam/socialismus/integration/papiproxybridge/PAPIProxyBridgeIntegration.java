package me.whereareiam.socialismus.integration.papiproxybridge;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.whereareiam.socialismus.api.input.Registry;
import me.whereareiam.socialismus.api.model.player.DummyPlayer;
import me.whereareiam.socialismus.api.output.integration.FormattingIntegration;
import net.william278.papiproxybridge.api.PlaceholderAPI;

@Singleton
public class PAPIProxyBridgeIntegration implements FormattingIntegration {
	private final PlaceholderAPI placeholderAPI = PlaceholderAPI.createInstance();
	private final Registry<FormattingIntegration> registry;

	@Inject
    public PAPIProxyBridgeIntegration(Registry<FormattingIntegration> registry) {this.registry = registry;}

    @Override
	public String format(DummyPlayer dummyPlayer, String content) {
		return placeholderAPI.formatPlaceholders(content, dummyPlayer.getUniqueId()).getNow(content);
	}

	@Override
	public boolean isAvailable() {
		try {
			Class.forName("net.william278.papiproxybridge.api.PlaceholderAPI");
			registry.register(this);

			return true;
		} catch (ClassNotFoundException e) {
			return false;
		}
	}
}
