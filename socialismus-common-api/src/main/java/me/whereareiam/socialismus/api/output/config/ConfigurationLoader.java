package me.whereareiam.socialismus.api.output.config;

import java.io.InputStream;
import java.nio.file.Path;

public interface ConfigurationLoader {
    <T> T load(Path path, Class<T> clazz);

    <T> T load(InputStream stream, Class<T> clazz);
}
