package me.whereareiam.socialismus.api.model.config.message;

import com.google.inject.Singleton;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Singleton
public class Messages {
	private CommandMessages commands;
}
