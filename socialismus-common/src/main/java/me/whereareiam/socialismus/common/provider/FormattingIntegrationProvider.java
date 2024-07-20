package me.whereareiam.socialismus.common.provider;

import com.google.inject.Provider;
import com.google.inject.Singleton;
import me.whereareiam.socialismus.api.input.registry.Registry;
import me.whereareiam.socialismus.api.output.integration.FormattingIntegration;

import java.util.HashSet;
import java.util.Set;

@Singleton
public class FormattingIntegrationProvider implements Provider<Set<FormattingIntegration>>, Registry<FormattingIntegration> {
    private final Set<FormattingIntegration> formattingIntegrations = new HashSet<>();

    @Override
    public void register(FormattingIntegration integration) {
        formattingIntegrations.add(integration);
    }

    @Override
    public Set<FormattingIntegration> get() {
        return formattingIntegrations;
    }
}
