package me.whereareiam.socialismus.platform.velocity.util;

import com.velocitypowered.api.event.PostOrder;

public class VelocityUtil {
    public static PostOrder of(me.whereareiam.socialismus.api.type.EventPriority priority) {
        return switch (priority) {
            case LOWEST -> PostOrder.FIRST;
            case LOW -> PostOrder.EARLY;
            case NORMAL -> PostOrder.NORMAL;
            case HIGH -> PostOrder.LATE;
            case HIGHEST -> PostOrder.LAST;
        };
    }
}
