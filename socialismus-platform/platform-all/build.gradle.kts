import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

tasks.withType<ShadowJar> {
    archiveClassifier.set("ALL")
}

dependencies {
    "implementation"(project(":socialismus-platform:platform-bukkit"))
    "implementation"(project(":socialismus-platform:platform-paper"))
    "implementation"(project(":socialismus-platform:platform-velocity"))
}