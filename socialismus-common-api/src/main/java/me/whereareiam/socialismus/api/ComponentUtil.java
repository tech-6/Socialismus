package me.whereareiam.socialismus.api;

import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;

@SuppressWarnings("unused")
public class ComponentUtil {
	@Getter
	private static final PlainTextComponentSerializer PLAIN_TEXT_SERIALIZER = PlainTextComponentSerializer.plainText();
	@Getter
	private static final MiniMessage MINI_MESSAGE_SERIALIZER = MiniMessage.miniMessage();
	@Getter
	private static final LegacyComponentSerializer LEGACY_SERIALIZER = LegacyComponentSerializer.legacyAmpersand();
	@Getter
	private static final LegacyComponentSerializer LEGACY_SECTION_SERIALIZER = LegacyComponentSerializer.legacySection();
	@Getter
	private static final GsonComponentSerializer GSON_SERIALIZER = GsonComponentSerializer.gson();
	@Getter
	private static final GsonComponentSerializer GSON_DOWNSAMPLING_SERIALIZER = GsonComponentSerializer.colorDownsamplingGson();

	public static String toPlain(Component component) {
		return PLAIN_TEXT_SERIALIZER.serialize(component);
	}

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
