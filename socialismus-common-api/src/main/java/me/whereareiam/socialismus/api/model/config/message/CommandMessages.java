package me.whereareiam.socialismus.api.model.config.message;

import com.google.inject.Singleton;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Singleton
public class CommandMessages {
	private String noPermission;
	private String executionError;
	private String invalidSyntax;

	private String invalidSyntaxBoolean;
	private String invalidSyntaxNumber;
	private String invalidSyntaxString;
}
