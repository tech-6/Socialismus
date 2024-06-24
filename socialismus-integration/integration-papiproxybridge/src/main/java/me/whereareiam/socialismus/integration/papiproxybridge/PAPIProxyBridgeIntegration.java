package me.whereareiam.socialismus.integration.papiproxybridge;

import com.google.inject.Singleton;
import me.whereareiam.socialismus.api.model.DummyPlayer;
import me.whereareiam.socialismus.api.output.integration.FormattingIntegration;
import net.william278.papiproxybridge.api.PlaceholderAPI;

@Singleton
public class PAPIProxyBridgeIntegration implements FormattingIntegration {
	private final PlaceholderAPI placeholderAPI = PlaceholderAPI.createInstance();

	@Override
	public String format(DummyPlayer dummyPlayer, String content) {
		return placeholderAPI.formatPlaceholders(content, dummyPlayer.getUniqueId()).getNow(content);
	}

	@Override
	public boolean isAvailable() {
		try {
			Class.forName("net.william278.papiproxybridge.api.PlaceholderAPI");
			return true;
		} catch (ClassNotFoundException e) {
			return false;
		}
	}
}
