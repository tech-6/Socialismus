import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

tasks.withType<ShadowJar> {
    archiveClassifier.set("MODERN")
}

dependencies {
    "implementation"(project(":socialismus-platform:platform-paper"))
    "implementation"(project(":socialismus-platform:platform-velocity"))
}