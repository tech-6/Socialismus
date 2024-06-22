package me.whereareiam.socialismus.api.model.serializer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import me.whereareiam.socialismus.api.model.DummyPlayer;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class SerializerContent {
	private final DummyPlayer dummyPlayer;
	private final List<SerializerPlaceholder> placeholders;
	private String message;
}
