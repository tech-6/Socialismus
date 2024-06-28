package me.whereareiam.socialismus.api.model.requirement;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@ToString
@SuperBuilder(toBuilder = true)
public class PlaceholderRequirement extends Requirement {
	private String placeholder;
}
