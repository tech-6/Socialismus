package me.whereareiam.socialismus.api.input.requirement;

import me.whereareiam.socialismus.api.model.player.DummyPlayer;
import me.whereareiam.socialismus.api.model.requirement.RequirementGroup;

public interface RequirementEvaluatorService {
    boolean check(RequirementGroup group, DummyPlayer dummyPlayer);
}
