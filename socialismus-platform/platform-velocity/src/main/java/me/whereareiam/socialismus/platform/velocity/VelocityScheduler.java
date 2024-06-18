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
	private final com.velocitypowered.api.scheduler.Scheduler scheduler;
	private final Map<String, Map<Integer, ScheduledTask>> tasks = new HashMap<>();

	@Inject
	public VelocityScheduler(ProxyServer proxyServer) {
		this.scheduler = proxyServer.getScheduler();
	}

	@Override
	public void schedule(RunnableTask runnableTask) {
		ScheduledTask task = scheduler.buildTask(this, runnableTask.getRunnable()).schedule();
		tasks.put(runnableTask.getModule(), Map.of(runnableTask.getId(), task));
	}

	@Override
	public void schedule(DelayedRunnableTask runnableTask) {
		ScheduledTask task = scheduler.buildTask(this,
				runnableTask.getRunnable()).delay(Duration.ofMillis(runnableTask.getDelay())
		).schedule();
		tasks.put(runnableTask.getModule(), Map.of(runnableTask.getId(), task));
	}

	@Override
	public void schedule(PeriodicalRunnableTask runnableTask) {
		ScheduledTask task = scheduler.buildTask(this,
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
	public void cancel(RunnableTask runnableTask, boolean force) {
		cancel(runnableTask);
	}

	@Override
	public void cancel(DelayedRunnableTask runnableTask) {
		cancel((RunnableTask) runnableTask);
	}

	@Override
	public void cancel(DelayedRunnableTask runnableTask, boolean force) {
		cancel((RunnableTask) runnableTask, force);
	}

	@Override
	public void shutdown() {
		scheduler.tasksByPlugin(this).forEach(ScheduledTask::cancel);
		tasks.clear();
	}
}

