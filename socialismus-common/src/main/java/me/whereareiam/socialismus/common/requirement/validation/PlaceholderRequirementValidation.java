package me.whereareiam.socialismus.common.requirement.validation;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.whereareiam.socialismus.api.input.RequirementValidation;
import me.whereareiam.socialismus.api.input.registry.ExtendedRegistry;
import me.whereareiam.socialismus.api.model.player.DummyPlayer;
import me.whereareiam.socialismus.api.model.requirement.PlaceholderRequirement;
import me.whereareiam.socialismus.api.model.requirement.Requirement;
import me.whereareiam.socialismus.api.output.integration.FormattingIntegration;
import me.whereareiam.socialismus.api.output.integration.Integration;
import me.whereareiam.socialismus.api.type.requirement.RequirementType;

import java.util.Set;

@Singleton
public class PlaceholderRequirementValidation implements RequirementValidation {
    private final Set<Integration> integrations;

    @Inject
    public PlaceholderRequirementValidation(ExtendedRegistry<RequirementType, RequirementValidation> registry, Set<Integration> integrations) {
        this.integrations = integrations;
        registry.register(RequirementType.PLACEHOLDER, this);
    }

    @Override
    public boolean check(Requirement requirement, DummyPlayer dummyPlayer) {
        FormattingIntegration formatter = findFormatterIntegration();
        if (formatter == null || !(requirement instanceof PlaceholderRequirement pr))
            return false;

        return checkCondition(pr, formatter, dummyPlayer);
    }

    private FormattingIntegration findFormatterIntegration() {
        return integrations.stream()
                .filter(integration -> integration instanceof FormattingIntegration)
                .map(integration -> (FormattingIntegration) integration)
                .filter(integration -> integration.getName().equalsIgnoreCase("PlaceholderAPI")
                        || integration.getName().equalsIgnoreCase("PAPIProxyBridge"))
                .findFirst()
                .orElse(null);
    }

    private boolean checkCondition(PlaceholderRequirement pr, FormattingIntegration formatter, DummyPlayer dummyPlayer) {
        String formattedValue = formatter.format(dummyPlayer, pr.getPlaceholder());

        return switch (pr.getCondition()) {
            case EQUALS -> formattedValue.equals(pr.getExpected());
            case GREATER_THAN, LESS_THAN, GREATER_THAN_OR_EQUALS, LESS_THAN_OR_EQUALS ->
                    String.valueOf(compareNumericValues(pr, formattedValue)).equals(pr.getExpected());
            default -> false;
        };
    }

    private boolean compareNumericValues(PlaceholderRequirement pr, String formattedValue) {
        try {
            double value = Double.parseDouble(formattedValue);
            double expected = Double.parseDouble(pr.getExpected());

            return switch (pr.getCondition()) {
                case GREATER_THAN -> value > expected;
                case LESS_THAN -> value < expected;
                case GREATER_THAN_OR_EQUALS -> value >= expected;
                case LESS_THAN_OR_EQUALS -> value <= expected;
                default -> false;
            };
        } catch (NumberFormatException ignored) {
            return false;
        }
    }
}