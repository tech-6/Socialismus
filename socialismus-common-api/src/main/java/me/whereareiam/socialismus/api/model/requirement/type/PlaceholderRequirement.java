package me.whereareiam.socialismus.api.model.requirement.type;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import me.whereareiam.socialismus.api.model.requirement.Requirement;

import java.util.List;

@Getter
@Setter
@ToString
@SuperBuilder(toBuilder = true)
public class PlaceholderRequirement extends Requirement {
    private List<String> placeholders;
}
