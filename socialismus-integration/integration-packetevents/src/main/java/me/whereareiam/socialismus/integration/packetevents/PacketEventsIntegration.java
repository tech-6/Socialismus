package me.whereareiam.socialismus.integration.packetevents;

import com.google.inject.Singleton;
import me.whereareiam.socialismus.api.output.integration.Integration;

@Singleton
public class PacketEventsIntegration implements Integration {
	@Override
	public boolean isAvailable() {
		try {
			Class.forName("com.github.retrooper.packetevents.PacketEventsAPI");
			return true;
		} catch (ClassNotFoundException e) {
			return false;
		}
	}
}
