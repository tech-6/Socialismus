package me.whereareiam.socialismus.api.model.scheduler;

import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@ToString
@SuperBuilder(toBuilder = true)
public class PeriodicalRunnableTask extends RunnableTask {
	private final long delay;
	private final long period;
}