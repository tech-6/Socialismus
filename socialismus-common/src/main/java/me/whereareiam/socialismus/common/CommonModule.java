package me.whereareiam.socialismus.common;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import me.whereareiam.socialismus.api.input.chat.ChatMessageWorker;
import me.whereareiam.socialismus.api.output.integration.FormattingIntegration;
import me.whereareiam.socialismus.common.chat.ChatMessageProcessor;
import org.reflections.Reflections;

import java.util.Set;

@SuppressWarnings("unused")
public class CommonModule extends AbstractModule {
	@Override
	protected void configure() {
		bind(ChatMessageWorker.class).to(ChatMessageProcessor.class);

		Reflections reflections = new Reflections(this.getClass().getPackage().getName().split("\\.", 3)[0] + ".integration");
		Set<Class<? extends FormattingIntegration>> classes = reflections.getSubTypesOf(FormattingIntegration.class);

		for (Class<? extends FormattingIntegration> implementationClass : classes)
			bind(FormattingIntegration.class).to(implementationClass).in(Scopes.SINGLETON);
	}
}
