package me.whereareiam.socialismus.adapter.config.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Singleton;
import me.whereareiam.socialismus.api.model.requirement.PermissionRequirement;
import me.whereareiam.socialismus.api.model.requirement.Requirement;
import me.whereareiam.socialismus.api.model.requirement.WorldRequirement;
import me.whereareiam.socialismus.api.type.requirement.RequirementConditionType;

import java.io.IOException;
import java.util.List;

@Singleton
public class RequirementDeserializer extends JsonDeserializer<Requirement> {
    private static final List<RequirementConditionType> PLACEHOLDER_CONDITIONS = List.of(
            RequirementConditionType.EQUALS,
            RequirementConditionType.GREATER_THAN,
            RequirementConditionType.LESS_THAN,
            RequirementConditionType.GREATER_THAN_OR_EQUALS,
            RequirementConditionType.LESS_THAN_OR_EQUALS
    );

    private static final List<RequirementConditionType> PERMISSION_CONDITIONS = List.of(
            RequirementConditionType.HAS,
            RequirementConditionType.CONTAINS
    );

    @Override
    public Requirement deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException {
        ObjectCodec codec = parser.getCodec();
        JsonNode root = codec.readTree(parser);

        if (root.has("worlds")) {
            return codec.treeToValue(root, WorldRequirement.class);
        }

        if (root.has("placeholder")) {
            return handlePermissionRequirement(codec, root, PLACEHOLDER_CONDITIONS, RequirementConditionType.EQUALS);
        }

        if (root.has("permissions")) {
            return handlePermissionRequirement(codec, root, PERMISSION_CONDITIONS, RequirementConditionType.HAS);
        }

        return null;
    }

    private PermissionRequirement handlePermissionRequirement(ObjectCodec codec, JsonNode root, List<RequirementConditionType> validConditions, RequirementConditionType defaultCondition) throws IOException {
        PermissionRequirement pr = codec.treeToValue(root, PermissionRequirement.class);
        if (!validConditions.contains(pr.getCondition()))
            pr.setCondition(defaultCondition);

        return pr;
    }
}
