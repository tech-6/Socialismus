package me.whereareiam.socialismus.api.output;

import me.whereareiam.socialismus.api.model.scheduler.DelayedRunnableTask;
import me.whereareiam.socialismus.api.model.scheduler.PeriodicalRunnableTask;
import me.whereareiam.socialismus.api.model.scheduler.RunnableTask;

public interface Scheduler {
    void schedule(RunnableTask runnableTask);

    void schedule(DelayedRunnableTask runnableTask);

    void schedule(PeriodicalRunnableTask runnableTask);

    void schedule(RunnableTask runnableTask, boolean async);

    void schedule(DelayedRunnableTask runnableTask, boolean async);

    void schedule(PeriodicalRunnableTask runnableTask, boolean async);

    void cancel(RunnableTask runnableTask);

    void cancel(DelayedRunnableTask runnableTask);

    void cancelByModule(String module);
}
