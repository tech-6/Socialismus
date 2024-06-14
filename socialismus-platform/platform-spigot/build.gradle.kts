import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

tasks.withType<ShadowJar> {
    archiveClassifier.set("SPIGOT")
}

dependencies {
    "implementation"(project(":socialismus-platform"))
    "compileOnly"(libs.bundles.spigot)
}