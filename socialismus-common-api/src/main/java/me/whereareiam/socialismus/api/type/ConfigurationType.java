package me.whereareiam.socialismus.api.type;

import lombok.Getter;

@Getter
public enum ConfigurationType {
	YAML(".yml"),
	JSON(".json");

	private final String extension;

	ConfigurationType(String extension) {
		this.extension = extension;
	}
}
