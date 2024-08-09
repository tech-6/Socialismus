package me.whereareiam.socialismus.api.output.config;

import me.whereareiam.socialismus.api.output.DefaultConfig;
import me.whereareiam.socialismus.api.type.ConfigurationType;

public interface ConfigurationManager {
    ConfigurationType getConfigurationType();

    void addDeserializer(Class<?> clazz, Object deserializer);

    void addSerializer(Class<?> clazz, Object serializer);

    void addTemplate(Class<?> clazz, DefaultConfig<?> template);

    <T> DefaultConfig<T> getTemplate(Class<T> clazz);
}
