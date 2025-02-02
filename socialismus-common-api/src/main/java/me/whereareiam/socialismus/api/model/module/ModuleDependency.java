package me.whereareiam.socialismus.api.model.module;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import me.whereareiam.socialismus.api.type.DependencyType;

@Getter
@Setter
@ToString
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class ModuleDependency {
    private String name;
    private String version;
    private DependencyType type;
}
