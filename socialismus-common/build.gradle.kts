repositories {
    maven("https://s01.oss.sonatype.org/content/repositories/snapshots/")
}

dependencies {
    "implementation"(project(":socialismus-common-api"))
    "implementation"(libs.libbyCore)
    "compileOnly"(libs.bundles.adventure)
    "compileOnly"(libs.reflections)
}

tasks.register<Copy>("processSources") {
    from("src/main/java")
    into(layout.buildDirectory.dir("processed-src").get().asFile)
    include("**/*.java")
    filter { line ->
        line.replace("@version@", rootProject.version.toString())
            .replace("@guiceVersion@", rootProject.libs.versions.guice.get())
            .replace("@reflectionsVersion@", rootProject.libs.versions.reflections.get())
            .replace("@jacksonVersion@", rootProject.libs.versions.jackson.get())
            .replace("@adventureBukkitVersion@", rootProject.libs.versions.adventurePlatformBukkit.get())
            .replace("@cloudVersion@", rootProject.libs.versions.cloud.get())
            .replace("@cloudBukkitVersion@", rootProject.libs.versions.cloudBukkit.get())
            .replace("@cloudPaperVersion@", rootProject.libs.versions.cloudPaper.get())
            .replace("@cloudVelocityVersion@", rootProject.libs.versions.cloudVelocity.get())
            .replace("@cloudMinecraftExtrasVersion@", rootProject.libs.versions.cloudMinecraftExtras.get())
    }
}

tasks.named<JavaCompile>("compileJava") {
    dependsOn("processSources")
    source = fileTree(layout.buildDirectory.dir("processed-src").get().asFile)
}