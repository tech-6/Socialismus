package me.whereareiam.socialismus.common.serializer;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import lombok.Getter;
import me.whereareiam.socialismus.api.input.serializer.SerializationService;
import me.whereareiam.socialismus.api.input.serializer.SerializationWorker;
import me.whereareiam.socialismus.api.model.Worker;
import me.whereareiam.socialismus.api.model.config.Settings;
import me.whereareiam.socialismus.api.model.config.message.Messages;
import me.whereareiam.socialismus.api.model.player.DummyPlayer;
import me.whereareiam.socialismus.api.model.serializer.SerializerContent;
import me.whereareiam.socialismus.api.model.serializer.SerializerPlaceholder;
import me.whereareiam.socialismus.api.output.integration.FormattingIntegration;
import me.whereareiam.socialismus.api.output.integration.Integration;
import me.whereareiam.socialismus.api.type.SerializationType;
import net.kyori.adventure.text.Component;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Set;
import java.util.stream.Collectors;

@Singleton
public class Serializer implements SerializationService, SerializationWorker {
    private final Provider<Messages> messages;
    private final SerializationType serializationType;
    private final Set<FormattingIntegration> formatters;

    @Getter
    private final LinkedList<Worker<SerializerContent>> workers = new LinkedList<>();

    @Inject
    public Serializer(Provider<Messages> messages, Provider<Settings> settings, Set<Integration> integrations) {
        this.messages = messages;
        this.serializationType = settings.get().getSerializer();
        this.formatters = integrations.stream()
                .filter(FormattingIntegration.class::isInstance)
                .map(FormattingIntegration.class::cast)
                .collect(Collectors.toSet());
    }

    @Override
    public Component format(DummyPlayer dummyPlayer, String message) {
        return format(new SerializerContent(dummyPlayer, new ArrayList<>(), message));
    }

    @Override
    public Component format(SerializerContent content) {
        if (content.getMessage().isEmpty()) return Component.empty();

        content.setMessage(content.getMessage().replace("{prefix}", messages.get().getPrefix()));
        content.setMessage(hookIntegrations(content));

        for (SerializerPlaceholder placeholder : content.getPlaceholders())
            content.setMessage(content.getMessage().replace(placeholder.getPlaceholder(), placeholder.getValue()));

        for (Worker<SerializerContent> worker : workers)
            content = worker.getFunction().apply(content);

        return serializationType.getSerializer().deserialize(content.getMessage());
    }

    private String hookIntegrations(SerializerContent content) {
        for (FormattingIntegration formatter : formatters)
            if (formatter.isAvailable())
                content.setMessage(formatter.format(content.getDummyPlayer(), content.getMessage()));

        return content.getMessage();
    }

    @Override
    public boolean removeWorker(Worker<SerializerContent> worker) {
        if (!worker.isRemovable())
            return false;

        workers.remove(worker);
        return true;
    }

    @Override
    public void addWorker(Worker<SerializerContent> worker) {
        if (workers.stream().anyMatch(w -> w.getPriority() == worker.getPriority()))
            return;

        workers.add(worker);
        workers.sort((a, b) -> Integer.compare(b.getPriority(), a.getPriority()));
    }
}
