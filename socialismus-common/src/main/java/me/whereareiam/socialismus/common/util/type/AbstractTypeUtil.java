package me.whereareiam.socialismus.common.util.type;

public abstract class AbstractTypeUtil {
	protected static boolean isClassPresent(String className) {
		try {
			Class.forName(className);
			return true;
		} catch (ClassNotFoundException e) {
			return false;
		}
	}
}
