import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

tasks.withType<ShadowJar> {
    archiveClassifier.set("PAPER")
}

dependencies {
    "implementation"(project(":socialismus-platform"))
    "compileOnly"(libs.bundles.paper)
}