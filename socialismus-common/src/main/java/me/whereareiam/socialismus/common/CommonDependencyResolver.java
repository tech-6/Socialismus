package me.whereareiam.socialismus.common;

import com.alessiodp.libby.Library;
import me.whereareiam.socialismus.api.input.DependencyResolver;

import java.util.ArrayList;
import java.util.List;

public abstract class CommonDependencyResolver implements DependencyResolver {
	protected final List<Library> libraries = new ArrayList<>();

	@Override
	public void loadLibraries() {
		// Common libraries

		addDependency("com.google.inject", "guice", Constants.getGuiceVersion(), true);
		addDependency("com.google.code.gson", "gson", Constants.getGsonVersion(), true);
		addDependency("org.reflections", "reflections", Constants.getReflectionsVersion(), true);
	}

	@Override
	public void addDependency(String groupId, String artifactId, String version) {
		libraries.add(Library.builder()
				.groupId(groupId)
				.artifactId(artifactId)
				.version(version)
				.build());
	}

	@Override
	public void addDependency(String groupId, String artifactId, String version, boolean resolveTransitiveDependencies) {
		libraries.add(Library.builder()
				.groupId(groupId)
				.artifactId(artifactId)
				.version(version)
				.resolveTransitiveDependencies(resolveTransitiveDependencies)
				.build());
	}

	@Override
	public void clearDependencies() {
		libraries.clear();
	}
}
