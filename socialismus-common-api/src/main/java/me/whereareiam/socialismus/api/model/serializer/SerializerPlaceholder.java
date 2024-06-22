package me.whereareiam.socialismus.api.model.serializer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class SerializerPlaceholder {
	private final String placeholder;
	private final String value;
}
