package me.whereareiam.socialismus.api.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import me.whereareiam.socialismus.api.type.EventPriority;

@Getter
@ToString
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class Event {
    private boolean register;
    private EventPriority priority;
}
