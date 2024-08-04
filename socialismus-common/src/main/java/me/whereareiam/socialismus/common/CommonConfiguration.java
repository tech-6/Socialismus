package me.whereareiam.socialismus.common;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.google.inject.name.Names;
import me.whereareiam.socialismus.api.Reloadable;
import me.whereareiam.socialismus.api.input.PluginInteractor;
import me.whereareiam.socialismus.api.input.WorkerProcessor;
import me.whereareiam.socialismus.api.input.chat.ChatContainerService;
import me.whereareiam.socialismus.api.input.chat.RequirementValidation;
import me.whereareiam.socialismus.api.input.registry.ExtendedRegistry;
import me.whereareiam.socialismus.api.input.registry.Registry;
import me.whereareiam.socialismus.api.input.serializer.SerializationService;
import me.whereareiam.socialismus.api.model.chat.message.ChatMessage;
import me.whereareiam.socialismus.api.model.chat.message.FormattedChatMessage;
import me.whereareiam.socialismus.api.model.serializer.SerializerContent;
import me.whereareiam.socialismus.api.output.integration.Integration;
import me.whereareiam.socialismus.api.type.requirement.RequirementType;
import me.whereareiam.socialismus.common.chat.ChatContainer;
import me.whereareiam.socialismus.common.chat.processor.ChatMessageProcessor;
import me.whereareiam.socialismus.common.chat.processor.FormattedChatMessageProcessor;
import me.whereareiam.socialismus.common.provider.IntegrationProvider;
import me.whereareiam.socialismus.common.provider.ReloadableProvider;
import me.whereareiam.socialismus.common.requirement.RequirementRegistry;
import me.whereareiam.socialismus.common.requirement.validation.PermissionRequirementValidation;
import me.whereareiam.socialismus.common.requirement.validation.WorldRequirementValidation;
import me.whereareiam.socialismus.common.serializer.Serializer;

import java.util.Set;

@SuppressWarnings("unused")
public class CommonConfiguration extends AbstractModule {
    @Override
    protected void configure() {
        bind(PluginInteractor.class).to(CommonPluginInteractor.class);

        bind(ChatContainerService.class).to(ChatContainer.class);
        bind(new TypeLiteral<WorkerProcessor<ChatMessage>>() {}).to(ChatMessageProcessor.class);
        bind(new TypeLiteral<WorkerProcessor<FormattedChatMessage>>() {}).to(FormattedChatMessageProcessor.class);

        bind(SerializationService.class).to(Serializer.class);
        bind(new TypeLiteral<WorkerProcessor<SerializerContent>>() {}).to(Serializer.class);

        bind(new TypeLiteral<Registry<Integration>>() {}).to(IntegrationProvider.class);
        bind(new TypeLiteral<Set<Integration>>() {}).toProvider(IntegrationProvider.class).asEagerSingleton();

        bind(new TypeLiteral<Registry<Reloadable>>() {}).to(ReloadableProvider.class);
        bind(new TypeLiteral<Set<Reloadable>>() {}).annotatedWith(Names.named("reloadables")).toProvider(ReloadableProvider.class).asEagerSingleton();

        bind(PermissionRequirementValidation.class).asEagerSingleton();
        bind(WorldRequirementValidation.class).asEagerSingleton();
        bind(new TypeLiteral<ExtendedRegistry<RequirementType, RequirementValidation>>() {}).to(RequirementRegistry.class);
    }
}
