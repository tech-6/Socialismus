package me.whereareiam.socialismus.api.input;

import me.whereareiam.socialismus.api.model.Worker;

import java.util.LinkedList;

public interface WorkerProcessor<T> {
	LinkedList<Worker<T>> getWorkers();

	boolean removeWorker(Worker<T> worker);

	void addWorker(Worker<T> worker);
}
