package me.whereareiam.socialismus.adapter.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import me.whereareiam.socialismus.api.type.ConfigurationType;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.stream.Stream;

@Singleton
public class ConfigManager implements Provider<ObjectMapper> {
	private final Path dataPath;

	@Inject
	public ConfigManager(@Named("dataPath") Path dataPath) {
		this.dataPath = dataPath;
	}

	public ConfigurationType getConfigurationType() {
		try (Stream<Path> paths = Files.list(this.dataPath)) {
			Optional<Path> configFile = paths
					.filter(Files::isRegularFile)
					.filter(file -> file.getFileName().toString().startsWith("type"))
					.findFirst();

			if (configFile.isPresent()) {
				String[] parts = configFile.get().getFileName().toString().split("=");

				return ConfigurationType.valueOf(parts[1].toUpperCase());
			} else {
				Files.createFile(this.dataPath.resolve("type=YAML"));

				return ConfigurationType.YAML;
			}
		} catch (IOException e) {
			throw new RuntimeException("Failed to get configuration type", e);
		}
	}

	@Override
	public ObjectMapper get() {
		ObjectMapper objectMapper;

		switch (getConfigurationType()) {
			case JSON -> objectMapper = new JsonMapper();
			case YAML -> {
				YAMLFactory yamlFactory = new YAMLFactory()
						.disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER)
						.enable(YAMLGenerator.Feature.INDENT_ARRAYS)
						.enable(YAMLGenerator.Feature.INDENT_ARRAYS_WITH_INDICATOR);
				objectMapper = new YAMLMapper(yamlFactory);
			}

			default -> throw new IllegalArgumentException("Unsupported configuration type");
		}

		return objectMapper;
	}
}
