package me.whereareiam.socialismus.api.input;

public interface DependencyResolver {
	void resolveDependencies();

	void loadLibraries();

	void addDependency(String groupId, String artifactId, String version);

	void addDependency(String groupId, String artifactId, String version, boolean resolveTransitiveDependencies);

	void clearDependencies();
}
