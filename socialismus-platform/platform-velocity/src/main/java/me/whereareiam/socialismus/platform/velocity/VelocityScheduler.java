package me.whereareiam.socialismus.platform.velocity;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.scheduler.ScheduledTask;
import me.whereareiam.socialismus.api.model.scheduler.DelayedRunnableTask;
import me.whereareiam.socialismus.api.model.scheduler.PeriodicalRunnableTask;
import me.whereareiam.socialismus.api.model.scheduler.RunnableTask;
import me.whereareiam.socialismus.api.output.Scheduler;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Singleton
public class VelocityScheduler implements Scheduler {
    private final VelocitySocialismus socialismus;
    private final com.velocitypowered.api.scheduler.Scheduler scheduler;
    private final Map<String, Map<Integer, ScheduledTask>> tasks = new HashMap<>();

    @Inject
    public VelocityScheduler(VelocitySocialismus socialismus, ProxyServer proxyServer) {
        this.socialismus = socialismus;
        this.scheduler = proxyServer.getScheduler();
    }

    @Override
    public void schedule(RunnableTask runnableTask) {
        ScheduledTask task = scheduler.buildTask(socialismus, runnableTask.getRunnable()).schedule();
        tasks.put(runnableTask.getModule(), Map.of(runnableTask.getId(), task));
    }

    @Override
    public void schedule(DelayedRunnableTask runnableTask) {
        ScheduledTask task = scheduler.buildTask(socialismus,
                runnableTask.getRunnable()).delay(Duration.ofMillis(runnableTask.getDelay())
        ).schedule();
        tasks.put(runnableTask.getModule(), Map.of(runnableTask.getId(), task));
    }

    @Override
    public void schedule(PeriodicalRunnableTask runnableTask) {
        ScheduledTask task = scheduler.buildTask(socialismus,
                runnableTask.getRunnable()).repeat(Duration.ofMillis(runnableTask.getDelay())
        ).schedule();

        tasks.put(runnableTask.getModule(), Map.of(runnableTask.getId(), task));
    }

    @Override
    public void schedule(RunnableTask runnableTask, boolean async) {
        schedule(runnableTask);
    }

    @Override
    public void schedule(DelayedRunnableTask runnableTask, boolean async) {
        schedule(runnableTask);
    }

    @Override
    public void schedule(PeriodicalRunnableTask runnableTask, boolean async) {
        schedule(runnableTask);
    }

    @Override
    public void cancel(RunnableTask runnableTask) {
        if (tasks.containsKey(runnableTask.getModule()) && tasks.get(runnableTask.getModule()).containsKey(runnableTask.getId())) {
            tasks.get(runnableTask.getModule()).get(runnableTask.getId()).cancel();
            tasks.get(runnableTask.getModule()).remove(runnableTask.getId());
        }
    }

    @Override
    public void cancel(DelayedRunnableTask runnableTask) {
        cancel((RunnableTask) runnableTask);
    }

    @Override
    public void cancelByModule(String module) {
        if (tasks.containsKey(module)) {
            tasks.get(module).values().forEach(ScheduledTask::cancel);
            tasks.remove(module);
        }
    }
}

