package me.whereareiam.socialismus.api.output;

import me.whereareiam.socialismus.api.model.scheduler.DelayedRunnableTask;
import me.whereareiam.socialismus.api.model.scheduler.PeriodicalRunnableTask;
import me.whereareiam.socialismus.api.model.scheduler.RunnableTask;

public interface Scheduler {
	void schedule(RunnableTask runnableTask);

	void schedule(DelayedRunnableTask scheduledTask);

	void schedule(PeriodicalRunnableTask scheduledTask);

	void schedule(RunnableTask runnableTask, boolean async);

	void schedule(DelayedRunnableTask scheduledTask, boolean async);

	void schedule(PeriodicalRunnableTask scheduledTask, boolean async);

	void cancel(RunnableTask runnableTask);

	void cancel(RunnableTask runnableTask, boolean force);

	void cancel(DelayedRunnableTask scheduledTask);

	void cancel(DelayedRunnableTask scheduledTask, boolean force);

	void shutdown();
}
