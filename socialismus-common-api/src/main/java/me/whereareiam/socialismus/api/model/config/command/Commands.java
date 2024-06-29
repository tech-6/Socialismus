package me.whereareiam.socialismus.api.model.config.command;

import com.google.inject.Singleton;
import lombok.Getter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@Getter
@ToString
@Singleton
public class Commands {
	private Map<String, Command> commands = new HashMap<>();
}
