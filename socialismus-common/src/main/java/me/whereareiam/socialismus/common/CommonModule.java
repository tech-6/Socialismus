package me.whereareiam.socialismus.common;

import com.google.inject.AbstractModule;
import me.whereareiam.socialismus.api.input.ChatMessageWorker;
import me.whereareiam.socialismus.common.chat.ChatMessageProcessor;

@SuppressWarnings("unused")
public class CommonModule extends AbstractModule {
	@Override
	protected void configure() {
		bind(ChatMessageWorker.class).to(ChatMessageProcessor.class);
	}
}
