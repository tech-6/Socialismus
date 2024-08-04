dependencies {
    "compileOnly"(libs.bundles.bStats)
}

tasks.register<Copy>("processSources") {
    from("src/main/java")
    into(layout.buildDirectory.dir("processed-src").get().asFile)
    include("**/*.java")
    filter { line ->
        line.replace("@version@", rootProject.version.toString())
    }
}

tasks.named<JavaCompile>("compileJava") {
    dependsOn("processSources")
    source = fileTree(layout.buildDirectory.dir("processed-src").get().asFile)
}