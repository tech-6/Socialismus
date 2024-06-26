package me.whereareiam.socialismus.api.model.config.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class Chat {
	private String id;
	private int priority;
	private boolean enabled;
	private ChatParameters parameters;
	private List<ChatFormat> formats;
}
