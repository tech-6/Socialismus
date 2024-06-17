import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.apache.tools.ant.filters.ReplaceTokens

tasks.withType<ShadowJar> {
    archiveClassifier.set("VELOCITY")
}

dependencies {
    "compileOnly"(libs.bundles.velocity)
    "implementation"(rootProject.libs.libbyVelocity)
}

