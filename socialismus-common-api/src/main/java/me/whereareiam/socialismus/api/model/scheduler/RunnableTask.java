package me.whereareiam.socialismus.api.model.scheduler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@ToString
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class RunnableTask {
	private final int id;
	private final String module;

	private final Runnable runnable;
}
