package me.whereareiam.socialismus.core.version;

import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.Map;

public enum Version {
	V1_19_4(0),
	V1_20_0(0),
	V1_20_1(0),
	V1_20_2(1),
	V1_20_3(1),
	V1_20_4(1),
	V1_20_5(1),
	V1_20_6(1),
	V1_21_0(1);

	private static final Map<String, Version> VERSION_MAP = new HashMap<>();

	static {
		initialize();
	}

	Version(int id) {
	}

	public static Version getVersion() {
		String detailedVersion = Bukkit.getBukkitVersion();
		String version = detailedVersion.split("-")[0];
		Version result = VERSION_MAP.get(version);

		if (result == null) {
			throw new UnsupportedOperationException("Unsupported server version: " + detailedVersion);
		}

		return result;
	}

	private static void initialize() {
		VERSION_MAP.put("1.19.4", V1_19_4);
		VERSION_MAP.put("1.20", V1_20_0);
		VERSION_MAP.put("1.20.1", V1_20_1);
		VERSION_MAP.put("1.20.2", V1_20_2);
		VERSION_MAP.put("1.20.3", V1_20_3);
		VERSION_MAP.put("1.20.4", V1_20_4);
		VERSION_MAP.put("1.20.5", V1_20_5);
	}
}
