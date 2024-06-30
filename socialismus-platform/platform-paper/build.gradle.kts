import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

tasks.withType<ShadowJar> {
    archiveClassifier.set("PAPER")
}

dependencies {
    "compileOnly"(libs.bundles.paper)
    "implementation"(libs.libby.paper)
}