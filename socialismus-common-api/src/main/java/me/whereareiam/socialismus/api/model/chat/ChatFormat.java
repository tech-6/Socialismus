package me.whereareiam.socialismus.api.model.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import me.whereareiam.socialismus.api.model.requirement.RequirementGroup;
import me.whereareiam.socialismus.api.type.Participants;

import java.util.Map;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ChatFormat {
    private String format;
    private Map<Participants, RequirementGroup> requirements;
}
