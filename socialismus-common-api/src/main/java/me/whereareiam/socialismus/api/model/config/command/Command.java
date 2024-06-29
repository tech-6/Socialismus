package me.whereareiam.socialismus.api.model.config.command;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Command {
	private List<String> aliases;
	private String permission;
	private String description;
	private String usage;
	private boolean enabled;
}
