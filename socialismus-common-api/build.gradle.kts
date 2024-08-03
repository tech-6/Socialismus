dependencies {
    "compileOnly"(libs.bundles.adventure)
}

tasks.register("generateJavadocs", Javadoc::class) {
    source = sourceSets["main"].allJava
    classpath = configurations["compileClasspath"]
    destinationDir = file("$buildDir/generated/javadoc")

    options {
        title = "Socialismus API"
        windowTitle = "Socialismus API"
    }
}

tasks.register("javadocJar", Jar::class) {
    archiveClassifier.set("javadoc")
    from(tasks["generateJavadocs"])
}

tasks.named("build") {
    dependsOn("javadocJar")
}