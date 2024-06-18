package me.whereareiam.socialismus.api.model.scheduler;

import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@ToString
@SuperBuilder(toBuilder = true)
public class DelayedRunnableTask extends RunnableTask {
	private final long delay;
}
