repositories {
    maven("https://s01.oss.sonatype.org/content/repositories/snapshots/")
}

dependencies {
    "implementation"(project(":socialismus-common-api"))
    "implementation"(libs.libbyCore)
    "compileOnly"(libs.bundles.adventure)
}

tasks.register<Copy>("processSources") {
    from("src/main/java")
    into(layout.buildDirectory.dir("processed-src").get().asFile)
    include("**/*.java")
    filter { line ->
        line.replace("@guiceVersion@", rootProject.libs.versions.guice.get())
            .replace("@gsonVersion@", rootProject.libs.versions.gson.get())
            .replace("@version@", rootProject.version.toString())
    }
}

tasks.named<JavaCompile>("compileJava") {
    dependsOn("processSources")
    source = fileTree(layout.buildDirectory.dir("processed-src").get().asFile)
}