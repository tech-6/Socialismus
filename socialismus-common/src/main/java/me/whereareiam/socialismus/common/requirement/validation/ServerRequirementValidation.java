package me.whereareiam.socialismus.common.requirement.validation;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.whereareiam.socialismus.api.PlatformType;
import me.whereareiam.socialismus.api.input.RequirementValidation;
import me.whereareiam.socialismus.api.input.registry.ExtendedRegistry;
import me.whereareiam.socialismus.api.model.player.DummyPlayer;
import me.whereareiam.socialismus.api.model.requirement.Requirement;
import me.whereareiam.socialismus.api.model.requirement.ServerRequirement;
import me.whereareiam.socialismus.api.type.requirement.RequirementType;

@Singleton
public class ServerRequirementValidation implements RequirementValidation {
    @Inject
    public ServerRequirementValidation(ExtendedRegistry<RequirementType, RequirementValidation> registry) {
        registry.register(RequirementType.SERVER, this);
    }

    @Override
    public boolean check(Requirement requirement, DummyPlayer dummyPlayer) {
        if (!(requirement instanceof ServerRequirement sr)) return false;
        if (!PlatformType.isProxy()) return false;

        boolean checkResult = false;
        switch (sr.getCondition()) {
            case EQUALS ->
                    checkResult = sr.getServers().size() == 1 && sr.getServers().getFirst().equals(dummyPlayer.getLocation());
            case CONTAINS -> checkResult = sr.getServers().contains(dummyPlayer.getLocation());
        }

        String[] expectedValues = sr.getExpected().split("\\|");
        for (String expectedValue : expectedValues)
            if (String.valueOf(checkResult).equals(expectedValue))
                return true;

        return false;
    }
}
