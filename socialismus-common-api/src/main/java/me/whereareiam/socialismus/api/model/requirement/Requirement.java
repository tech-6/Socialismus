package me.whereareiam.socialismus.api.model.requirement;

import lombok.*;
import lombok.experimental.SuperBuilder;
import me.whereareiam.socialismus.api.type.requirement.RequirementConditionType;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class Requirement {
    private RequirementConditionType condition;
    private String expected;
}