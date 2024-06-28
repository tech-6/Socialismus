package me.whereareiam.socialismus.api.output.config;

import java.nio.file.Path;

public interface ConfigurationLoader {
	<T> T load(Path path, Class<T> clazz);
}
