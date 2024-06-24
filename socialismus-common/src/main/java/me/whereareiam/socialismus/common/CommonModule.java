package me.whereareiam.socialismus.common;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.Multibinder;
import me.whereareiam.socialismus.api.input.WorkerProcessor;
import me.whereareiam.socialismus.api.input.chat.ChatContainerService;
import me.whereareiam.socialismus.api.input.serializer.SerializationService;
import me.whereareiam.socialismus.api.model.chat.ChatMessage;
import me.whereareiam.socialismus.api.model.serializer.SerializerContent;
import me.whereareiam.socialismus.api.output.integration.FormattingIntegration;
import me.whereareiam.socialismus.common.chat.ChatContainer;
import me.whereareiam.socialismus.common.chat.ChatMessageProcessor;
import me.whereareiam.socialismus.common.serializer.Serializer;
import org.reflections.Reflections;

@SuppressWarnings("unused")
public class CommonModule extends AbstractModule {
	@Override
	protected void configure() {
		bind(ChatContainerService.class).to(ChatContainer.class);
		bind(new TypeLiteral<WorkerProcessor<ChatMessage>>() {
		}).to(ChatMessageProcessor.class);

		bind(SerializationService.class).to(Serializer.class);
		bind(new TypeLiteral<WorkerProcessor<SerializerContent>>() {
		}).to(Serializer.class);

		Multibinder<FormattingIntegration> multibinder = Multibinder.newSetBinder(binder(), FormattingIntegration.class);
		Reflections reflections = new Reflections(this.getClass().getPackage().getName().split("\\.", 3)[0] + ".integration");
		for (Class<? extends FormattingIntegration> implementationClass : reflections.getSubTypesOf(FormattingIntegration.class))
			multibinder.addBinding().to(implementationClass).in(Scopes.SINGLETON);
	}
}
