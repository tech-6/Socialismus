package me.whereareiam.socialismus.api.output.config;

public interface ConfigurationMerger {
	<T> void merge(T config, T defaultConfig);
}
