package me.whereareiam.socialismus.api.input.serializer;

import me.whereareiam.socialismus.api.model.player.DummyPlayer;
import me.whereareiam.socialismus.api.model.serializer.SerializerContent;
import net.kyori.adventure.text.Component;

public interface SerializationService {
	Component format(DummyPlayer dummyPlayer, String message);

	Component format(SerializerContent content);
}
