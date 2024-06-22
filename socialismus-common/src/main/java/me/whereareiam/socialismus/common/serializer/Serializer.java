package me.whereareiam.socialismus.common.serializer;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.whereareiam.socialismus.api.model.config.Settings;
import me.whereareiam.socialismus.api.model.serializer.SerializerContent;
import me.whereareiam.socialismus.api.output.integration.FormattingIntegration;
import me.whereareiam.socialismus.api.type.SerializationType;
import net.kyori.adventure.text.Component;

import java.util.Set;

@Singleton
public class Serializer {
	private final SerializationType serializationType;

	private final Set<FormattingIntegration> formatters;

	@Inject
	public Serializer(Settings settings, Set<FormattingIntegration> formatters) {
		this.serializationType = settings.getSerializer();
		this.formatters = formatters;
	}

	public Component format(SerializerContent content) {
		content.setMessage(hookIntegrations(content.getMessage()));

		return serializationType.getSerializer().deserialize(content.getMessage());
	}

	private String hookIntegrations(String message) {
		for (FormattingIntegration formatter : formatters) {
			if (formatter.isAvailable()) {
				message = formatter.format(message);
			}
		}

		return message;
	}
}
