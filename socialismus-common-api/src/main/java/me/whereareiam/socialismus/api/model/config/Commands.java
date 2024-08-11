package me.whereareiam.socialismus.api.model.config;

import lombok.Getter;
import lombok.ToString;
import me.whereareiam.socialismus.api.model.Command;

import java.util.HashMap;
import java.util.Map;

@Getter
@ToString
public class Commands {
    private Map<String, Command> commands = new HashMap<>();
}
