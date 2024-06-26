package me.whereareiam.socialismus.adapter.config.template;

import com.google.inject.Singleton;
import me.whereareiam.socialismus.api.model.config.Messages;
import me.whereareiam.socialismus.api.output.DefaultConfig;

@Singleton
public class MessagesTemplate implements DefaultConfig<Messages> {
	@Override
	public Messages getDefault() {
		Messages messages = new Messages();

		// Default values

		return messages;
	}
}
