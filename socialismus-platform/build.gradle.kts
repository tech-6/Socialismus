import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.apache.tools.ant.filters.ReplaceTokens

plugins {
    alias(libs.plugins.shadow)
}

subprojects {
    apply(plugin = "com.github.johnrengelman.shadow")

    tasks.withType<ShadowJar> {
        archiveBaseName.set(rootProject.name)
    }

    repositories {
        maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
        maven("https://repo.papermc.io/repository/maven-public/")
    }

    when (name) {
        "platform-paper", "platform-spigot" -> {
            tasks.named<Copy>("processResources") {
                filter<ReplaceTokens>("tokens" to mapOf(
                            "projectName" to rootProject.name,
                            "projectVersion" to project.version
                ))
            }
        }

        "platform-velocity" -> {
            tasks.register<Copy>("processSources") {
                from("src/main/java")
                into("$buildDir/processed-src")
                include("**/*.java")
                filter { line ->
                    line.replace("@projectName@", rootProject.name)
                        .replace("@projectVersion@", project.version.toString())
                }
            }

            tasks.named<JavaCompile>("compileJava") {
                dependsOn("processSources")
                source = fileTree("$buildDir/processed-src")
            }
        }
    }
}