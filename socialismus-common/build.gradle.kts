repositories {
    maven("https://s01.oss.sonatype.org/content/repositories/snapshots/")
}

dependencies {
    "implementation"(project(":socialismus-common-api"))
    "implementation"(libs.libby.core)
    "compileOnly"(libs.bundles.adventure)
}

tasks.register<Copy>("processSources") {
    from("src/main/java")
    into(layout.buildDirectory.dir("processed-src").get().asFile)
    include("**/*.java")
    filter { line ->
        line.replace("@version@", rootProject.version.toString())
            .replace("@guiceVersion@", rootProject.libs.versions.guice.get())
            .replace("@jacksonVersion@", rootProject.libs.versions.jackson.get())
            .replace("@adventureBukkitVersion@", rootProject.libs.versions.adventure.platform.bukkit.get())
            .replace("@cloudVersion@", rootProject.libs.versions.cloud.core.get())
            .replace("@cloudBukkitVersion@", rootProject.libs.versions.cloud.bukkit.get())
            .replace("@cloudPaperVersion@", rootProject.libs.versions.cloud.paper.get())
            .replace("@cloudVelocityVersion@", rootProject.libs.versions.cloud.velocity.get())
            .replace("@cloudMinecraftExtrasVersion@", rootProject.libs.versions.cloud.minecraft.extras.get())
    }
}

tasks.named<JavaCompile>("compileJava") {
    dependsOn("processSources")
    source = fileTree(layout.buildDirectory.dir("processed-src").get().asFile)
}