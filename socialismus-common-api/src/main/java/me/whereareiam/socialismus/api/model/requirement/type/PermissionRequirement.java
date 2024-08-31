package me.whereareiam.socialismus.api.model.requirement.type;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import me.whereareiam.socialismus.api.model.requirement.Requirement;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class PermissionRequirement extends Requirement {
    private List<String> permissions;
}
