package me.whereareiam.socialismus.api.model.config;

import com.google.inject.Singleton;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Singleton
public class MiscellaneousSettings {
	private boolean allowLegacyParsing;
}