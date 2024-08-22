package me.whereareiam.socialismus.common.requirement.validation;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.whereareiam.socialismus.api.input.RequirementValidation;
import me.whereareiam.socialismus.api.input.registry.ExtendedRegistry;
import me.whereareiam.socialismus.api.model.player.DummyPlayer;
import me.whereareiam.socialismus.api.model.requirement.PermissionRequirement;
import me.whereareiam.socialismus.api.model.requirement.Requirement;
import me.whereareiam.socialismus.api.output.PlatformInteractor;
import me.whereareiam.socialismus.api.type.requirement.RequirementType;

@Singleton
public class PermissionRequirementValidation implements RequirementValidation {
    private final PlatformInteractor interactor;

    @Inject
    public PermissionRequirementValidation(ExtendedRegistry<RequirementType, RequirementValidation> registry, PlatformInteractor interactor) {
        this.interactor = interactor;

        registry.register(RequirementType.PERMISSION, this);
    }

    @Override
    public boolean check(Requirement requirement, DummyPlayer dummyPlayer) {
        if (!(requirement instanceof PermissionRequirement pr)) return false;

        boolean checkResult = false;
        switch (pr.getCondition()) {
            case HAS -> checkResult = pr.getPermissions().stream()
                    .allMatch(perm -> interactor.hasPermission(dummyPlayer, perm));
            case CONTAINS -> checkResult = pr.getPermissions().stream()
                    .anyMatch(perm -> interactor.hasPermission(dummyPlayer, perm));
        }

        return String.valueOf(checkResult).equals(pr.getExpected());
    }
}
