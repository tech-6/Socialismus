package me.whereareiam.socialismus.api.output.config;

import java.nio.file.Path;

public interface ConfigurationSaver {
	<T> void save(Path path, T object);
}
