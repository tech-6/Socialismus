package me.whereareiam.socialismus.api.model.module;

import lombok.*;
import lombok.experimental.SuperBuilder;
import me.whereareiam.socialismus.api.PlatformType;
import me.whereareiam.socialismus.api.type.Version;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class Module {
    private String name;
    private String version;
    private List<String> authors;

    private List<PlatformType> supportedPlatforms;
    private List<Version> supportedVersions;
    private List<ModuleDependency> dependencies;

    private String main;
}