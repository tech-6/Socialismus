package me.whereareiam.socialismus.platform.bukkit;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.whereareiam.socialismus.api.model.scheduler.DelayedRunnableTask;
import me.whereareiam.socialismus.api.model.scheduler.PeriodicalRunnableTask;
import me.whereareiam.socialismus.api.model.scheduler.RunnableTask;
import me.whereareiam.socialismus.api.output.Scheduler;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;

@Singleton
public class BukkitScheduler implements Scheduler {
	private final Plugin plugin;
	private final Map<String, Map<Integer, BukkitTask>> tasks = new HashMap<>();

	@Inject
	public BukkitScheduler(Plugin plugin) {
		this.plugin = plugin;
	}

	@Override
	public void schedule(RunnableTask runnableTask) {
		schedule(runnableTask, false);
	}

	@Override
	public void schedule(DelayedRunnableTask runnableTask) {
		schedule(runnableTask, false);
	}

	@Override
	public void schedule(PeriodicalRunnableTask runnableTask) {
		schedule(runnableTask, false);
	}

	@Override
	public void schedule(RunnableTask runnableTask, boolean async) {
		BukkitTask task;
		if (async) {
			task = Bukkit.getScheduler().runTaskAsynchronously(plugin, runnableTask.getRunnable());
		} else {
			task = Bukkit.getScheduler().runTask(plugin, runnableTask.getRunnable());
		}

		tasks.put(runnableTask.getModule(), Map.of(runnableTask.getId(), task));
	}

	@Override
	public void schedule(DelayedRunnableTask runnableTask, boolean async) {
		BukkitTask task;
		if (async) {
			task = Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, runnableTask.getRunnable(), runnableTask.getDelay());
		} else {
			task = Bukkit.getScheduler().runTaskLater(plugin, runnableTask.getRunnable(), runnableTask.getDelay());
		}

		tasks.put(runnableTask.getModule(), Map.of(runnableTask.getId(), task));
	}

	@Override
	public void schedule(PeriodicalRunnableTask runnableTask, boolean async) {
		BukkitTask task;
		if (async) {
			task = Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, runnableTask.getRunnable(), runnableTask.getDelay(), runnableTask.getPeriod());
		} else {
			task = Bukkit.getScheduler().runTaskTimer(plugin, runnableTask.getRunnable(), runnableTask.getDelay(), runnableTask.getPeriod());
		}

		tasks.put(runnableTask.getModule(), Map.of(runnableTask.getId(), task));
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
		Bukkit.getScheduler().cancelTasks(plugin);
		tasks.clear();
	}
}

