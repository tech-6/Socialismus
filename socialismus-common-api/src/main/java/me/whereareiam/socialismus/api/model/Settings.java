package me.whereareiam.socialismus.api.model;

import com.google.inject.Singleton;
import lombok.Getter;
import lombok.ToString;
import me.whereareiam.socialismus.api.type.SerializationType;

@Getter
@ToString
@Singleton
public class Settings {
	private int level = 2;
	private SerializationType serializer = SerializationType.MINIMESSAGE;
}
