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

@Singleton
public class RequirementDeserializer extends JsonDeserializer<Requirement> {
    @Override
    public Requirement deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException {
        ObjectCodec codec = parser.getCodec();
        JsonNode root = codec.readTree(parser);

        if (root.has("worlds")) return codec.treeToValue(root, WorldRequirement.class);

        if (root.has("permissions")) {
            PermissionRequirement pr = codec.treeToValue(root, PermissionRequirement.class);

            if (pr.getCondition() != RequirementConditionType.HAS && pr.getCondition() != RequirementConditionType.CONTAINS)
                pr.setCondition(RequirementConditionType.HAS);

            return pr;
        }

        return null;
    }
}
