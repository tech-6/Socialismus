repositories {
    maven("https://jitpack.io")
}

dependencies {
    "implementation"(project(":socialismus-common-api"))
}

tasks.register<Copy>("processSources") {
    from("src/main/java")
    into("$buildDir/processed-src")
    include("**/*.java")
    filter { line ->
        line.replace("@guiceVersion@", rootProject.libs.versions.guice.get())
            .replace("@guavaVersion@", rootProject.libs.versions.guava.get())
    }
}

tasks.named<JavaCompile>("compileJava") {
    dependsOn("processSources")
    source = fileTree("$buildDir/processed-src")
}