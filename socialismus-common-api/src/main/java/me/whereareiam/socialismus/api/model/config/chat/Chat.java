package me.whereareiam.socialismus.api.model.config.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import me.whereareiam.socialismus.api.model.requirement.Requirement;
import me.whereareiam.socialismus.api.type.requirement.RequirementType;

import java.util.List;
import java.util.Map;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class Chat {
	private String id;
	private int priority;
	private boolean enabled;
	private ChatParameters parameters;
	private List<ChatFormat> formats;
	private Map<RequirementType, ? extends Requirement> requirements;
}
