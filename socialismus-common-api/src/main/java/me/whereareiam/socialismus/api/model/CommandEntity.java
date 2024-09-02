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
public class CommandEntity {
    private boolean enabled;
    private List<String> aliases;
    private String permission;
    private String description;
    private String usage;

    private Cooldown cooldown;

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    @SuperBuilder(toBuilder = true)
    public static class Cooldown {
        private boolean enabled;
        private int duration;
        private String group;
    }
}
