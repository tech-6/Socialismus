package me.whereareiam.socialismus.api.input.serializer;

import me.whereareiam.socialismus.api.model.serializer.SerializerContent;
import net.kyori.adventure.text.Component;

public interface SerializationService {
	Component format(SerializerContent content);
}
