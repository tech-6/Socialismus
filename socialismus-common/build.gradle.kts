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
        line.replace("@guiceVersion@", rootProject.libs.versions.guice.get())
            .replace("@reflectionsVersion@", rootProject.libs.versions.reflections.get())
            .replace("@jacksonVersion@", rootProject.libs.versions.jackson.get())
            .replace("@adventureBukkitVersion@", rootProject.libs.versions.adventurePlatformBukkit.get())
            .replace("@version@", rootProject.version.toString())
    }
}

tasks.named<JavaCompile>("compileJava") {
    dependsOn("processSources")
    source = fileTree(layout.buildDirectory.dir("processed-src").get().asFile)
}