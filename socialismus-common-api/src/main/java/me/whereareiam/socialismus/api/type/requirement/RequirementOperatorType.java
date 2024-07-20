package me.whereareiam.socialismus.api.type.requirement;

public enum RequirementOperatorType {
    AND, // 	This operator would require all conditions to be met. If any condition fails, the entire check fails.
    OR, // 		This operator would pass if any of the conditions are met. Only if all conditions fail, does the entire check fail.
    XOR, // 	This operator would pass if exactly one of the conditions is met. If both conditions are met or if both fail, then the entire check fails.
    NOT, // 	This operator would invert the result of the condition check.
    NAND, // 	This operator would pass if not all conditions are met. It’s the inverse of the AND operator.
    NOR //     This operator would pass if none of the conditions are met. It’s the inverse of the OR operator.
}
