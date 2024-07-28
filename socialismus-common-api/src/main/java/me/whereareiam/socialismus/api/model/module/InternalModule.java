package me.whereareiam.socialismus.api.model.module;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import me.whereareiam.socialismus.api.output.module.SocialisticModule;
import me.whereareiam.socialismus.api.type.ModuleState;

import java.nio.file.Path;

@Getter
@Setter
@ToString
@SuperBuilder(toBuilder = true)
public class InternalModule extends Module {
    private Path path;
    private SocialisticModule module;

    private ModuleState state;
}
