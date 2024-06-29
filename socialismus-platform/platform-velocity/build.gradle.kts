import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

tasks.withType<ShadowJar> {
    archiveClassifier.set("VELOCITY")
}

dependencies {
    "compileOnly"(libs.bundles.velocity)
    "compileOnly"(libs.cloudVelocity)
    "annotationProcessor"(libs.velocity)
    "implementation"(rootProject.libs.libbyVelocity)
}