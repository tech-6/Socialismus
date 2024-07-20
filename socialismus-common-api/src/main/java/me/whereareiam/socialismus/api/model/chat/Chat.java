package me.whereareiam.socialismus.api.model.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import me.whereareiam.socialismus.api.model.requirement.RequirementGroup;
import me.whereareiam.socialismus.api.type.Participants;

import java.util.List;
import java.util.Map;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class Chat {
    private String id;
    private int priority;
    private boolean enabled;
    private ChatParameters parameters;
    private List<ChatFormat> formats;
    private Map<Participants, RequirementGroup> requirements;
}
