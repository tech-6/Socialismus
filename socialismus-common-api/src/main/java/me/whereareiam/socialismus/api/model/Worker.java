package me.whereareiam.socialismus.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.function.Function;

@Getter
@Setter
@ToString
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class Worker<T> {
	private final Function<T, T> function;
	private final int priority;
	private boolean removable;
	private boolean cancelled;
}
