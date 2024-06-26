package me.whereareiam.socialismus.adapter.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.Iterator;
import java.util.Map;

@Singleton
public class ConfigMerger {
	private final ObjectMapper objectMapper;

	@Inject
	public ConfigMerger(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	public <T> void merge(T config, T defaultConfig) {
		try {
			ObjectNode configNode = objectMapper.valueToTree(config);
			JsonNode defaultConfigNode = objectMapper.valueToTree(defaultConfig);

			for (Iterator<Map.Entry<String, JsonNode>> it = defaultConfigNode.fields(); it.hasNext(); ) {
				Map.Entry<String, JsonNode> entry = it.next();
				if (!configNode.has(entry.getKey()) || configNode.get(entry.getKey()).isNull()) {
					configNode.set(entry.getKey(), entry.getValue());
				}
			}

			objectMapper.readerForUpdating(config).readValue(configNode);
		} catch (Exception e) {
			throw new RuntimeException("Failed to merge configuration", e);
		}
	}
}