package me.whereareiam.socialismus.api.model.requirement;

import lombok.*;
import lombok.experimental.SuperBuilder;
import me.whereareiam.socialismus.api.type.requirement.RequirementOperatorType;
import me.whereareiam.socialismus.api.type.requirement.RequirementType;

import java.util.Map;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class RequirementGroup {
    private RequirementOperatorType operator;
    private Map<RequirementType, ? extends Requirement> groups;
}
