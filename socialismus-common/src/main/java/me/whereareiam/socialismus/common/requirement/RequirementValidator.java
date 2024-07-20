package me.whereareiam.socialismus.common.requirement;

import com.google.inject.Inject;
import me.whereareiam.socialismus.api.input.chat.RequirementValidation;
import me.whereareiam.socialismus.api.model.chat.message.ChatMessage;
import me.whereareiam.socialismus.api.model.requirement.Requirement;
import me.whereareiam.socialismus.api.type.requirement.RequirementType;

import java.util.Map;

public class RequirementValidator {
    private final RequirementRegistry requirementRegistry;

    @Inject
    public RequirementValidator(RequirementRegistry requirementRegistry) {
        this.requirementRegistry = requirementRegistry;
    }

    public boolean isRequirementMet(Map.Entry<RequirementType, ? extends Requirement> entry, ChatMessage chatMessage) {
        RequirementValidation checker = requirementRegistry.get(entry.getKey());
        if (checker == null) {
            return false;
        }
        return checker.check(entry.getValue(), chatMessage.getSender());
    }
}
