package me.whereareiam.socialismus.api.input.chat;

import me.whereareiam.socialismus.api.model.player.DummyPlayer;
import me.whereareiam.socialismus.api.model.requirement.Requirement;

public interface RequirementValidation {
    boolean check(Requirement requirement, DummyPlayer dummyPlayer);
}
