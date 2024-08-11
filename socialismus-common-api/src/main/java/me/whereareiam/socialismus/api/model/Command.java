package me.whereareiam.socialismus.api.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class Command {
    private List<String> aliases;
    private String permission;
    private String description;
    private String usage;
    private boolean enabled;
}
