package me.whereareiam.socialismus.adapter.config.management;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.whereareiam.socialismus.api.output.config.ConfigurationSaver;

import java.nio.file.Path;

@Singleton
public class ConfigSaver implements ConfigurationSaver {
	private final ObjectMapper objectMapper;

	@Inject
	public ConfigSaver(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	public <T> void save(Path path, T object) {
		try {
			objectMapper.writerWithDefaultPrettyPrinter().writeValue(path.toFile(), object);
		} catch (Exception e) {
			throw new RuntimeException("Failed to save configuration", e);
		}
	}
}