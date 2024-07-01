package me.whereareiam.socialismus.adapter.config.management;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.whereareiam.socialismus.api.output.config.ConfigurationMerger;

import java.util.Iterator;
import java.util.Map;

@Singleton
public class ConfigMerger implements ConfigurationMerger {
	private final ObjectMapper objectMapper;

	@Inject
	public ConfigMerger(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	public <T> void merge(T config, T defaultConfig) {
		try {
			ObjectNode configNode = objectMapper.valueToTree(config);
			JsonNode defaultConfigNode = objectMapper.valueToTree(defaultConfig);

			mergeNodes(configNode, defaultConfigNode);

			objectMapper.readerForUpdating(config).readValue(configNode);
		} catch (Exception e) {
			throw new RuntimeException("Failed to merge configuration", e);
		}
	}

	private void mergeNodes(ObjectNode configNode, JsonNode defaultConfigNode) {
		for (Iterator<Map.Entry<String, JsonNode>> it = defaultConfigNode.fields(); it.hasNext(); ) {
			Map.Entry<String, JsonNode> entry = it.next();
			String key = entry.getKey();
			JsonNode value = entry.getValue();

			if (!configNode.has(key) || configNode.get(key).isNull()) {
				configNode.set(key, value);
			} else if (configNode.get(key).isObject() && value.isObject()) {
				mergeNodes((ObjectNode) configNode.get(key), value);
			}
		}
	}
}