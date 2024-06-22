package me.whereareiam.socialismus.api.type;

import me.whereareiam.socialismus.api.ComponentUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.ComponentSerializer;

public enum SerializationType {
	LEGACY_AMPERSAND,
	LEGACY_SECTION,
	MINIMESSAGE,
	GSON,
	GSON_DOWNSAMPLING;

	public ComponentSerializer<Component, ?, String> getSerializer() {
		return switch (this) {
			case LEGACY_AMPERSAND -> ComponentUtil.getLEGACY_SERIALIZER();
			case LEGACY_SECTION -> ComponentUtil.getLEGACY_SECTION_SERIALIZER();
			case MINIMESSAGE -> ComponentUtil.getMINI_MESSAGE_SERIALIZER();
			case GSON -> ComponentUtil.getGSON_SERIALIZER();
			case GSON_DOWNSAMPLING -> ComponentUtil.getGSON_DOWNSAMPLING_SERIALIZER();
		};
	}
}
