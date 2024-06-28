package me.whereareiam.socialismus.api.model.requirement;

import lombok.*;
import lombok.experimental.SuperBuilder;
import me.whereareiam.socialismus.api.type.requirement.RequirementOperatorType;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class Requirement {
	private RequirementOperatorType operator;
	private String condition;
	private String expected;
}