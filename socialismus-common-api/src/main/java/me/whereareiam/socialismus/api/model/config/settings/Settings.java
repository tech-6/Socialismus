package me.whereareiam.socialismus.api.model.config.settings;

import com.google.inject.Singleton;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import me.whereareiam.socialismus.api.type.SerializationType;

@Getter
@Setter
@ToString
@Singleton
public class Settings {
	private int level;
	private SerializationType serializer;

	private MiscellaneousSettings misc;
}
