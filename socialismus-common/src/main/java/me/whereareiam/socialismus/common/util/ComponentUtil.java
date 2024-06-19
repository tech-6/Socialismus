package me.whereareiam.socialismus.common.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

@SuppressWarnings("unused")
public class ComponentUtil {
	private static final MiniMessage MINI_MESSAGE_SERIALIZER = MiniMessage.miniMessage();
	private static final LegacyComponentSerializer LEGACY_SERIALIZER = LegacyComponentSerializer.legacyAmpersand();
	private static final LegacyComponentSerializer LEGACY_SECTION_SERIALIZER = LegacyComponentSerializer.legacySection();
	private static final GsonComponentSerializer GSON_SERIALIZER = GsonComponentSerializer.gson();
	private static final GsonComponentSerializer GSON_DOWNSAMPLING_SERIALIZER = GsonComponentSerializer.colorDownsamplingGson();

	public static String toString(Component component) {
		return toString(component, false);
	}

	public static String toString(Component component, boolean section) {
		if (section) {
			return LEGACY_SECTION_SERIALIZER.serialize(component);
		} else {
			return LEGACY_SERIALIZER.serialize(component);
		}
	}

	public static Component toMiniMessage(String string) {
		return MINI_MESSAGE_SERIALIZER.deserialize(string);
	}

	public static Component toGson(String string) {
		return toGson(string, false);
	}

	public static Component toGson(String string, boolean downsampling) {
		if (downsampling) {
			return GSON_DOWNSAMPLING_SERIALIZER.deserialize(string);
		} else {
			return GSON_SERIALIZER.deserialize(string);
		}
	}
}
