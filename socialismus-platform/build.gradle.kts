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
        maven("https://jitpack.io")
    }

    dependencies {
        "implementation"(project(":socialismus-adapter-config"))
        "implementation"(project(":socialismus-adapter-database"))
        "implementation"(project(":socialismus-adapter-module"))
        "implementation"(project(":socialismus-adapter-packet"))
        "implementation"(project(":socialismus-adapter-redis"))
        "implementation"(project(":socialismus-platform"))
        "implementation"(project(":socialismus-common-api"))
        "implementation"(project(":socialismus-common"))
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

tasks.named<Jar>("jar") {
    dependsOn("shadowJar")
}