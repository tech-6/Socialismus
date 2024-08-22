package me.whereareiam.socialismus.common.requirement;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.whereareiam.socialismus.api.input.RequirementValidation;
import me.whereareiam.socialismus.api.model.player.DummyPlayer;
import me.whereareiam.socialismus.api.model.requirement.Requirement;
import me.whereareiam.socialismus.api.type.requirement.RequirementType;

import java.util.Map;

@Singleton
public class RequirementEvaluator {
    private final RequirementRegistry requirementRegistry;

    @Inject
    public RequirementEvaluator(RequirementRegistry requirementRegistry) {
        this.requirementRegistry = requirementRegistry;
    }

    public boolean isRequirementMet(Map.Entry<RequirementType, ? extends Requirement> entry, DummyPlayer dummyPlayer) {
        RequirementValidation checker = requirementRegistry.get(entry.getKey());
        if (checker == null)
            return false;

        return checker.check(entry.getValue(), dummyPlayer);
    }
}
