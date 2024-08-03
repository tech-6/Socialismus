plugins {
    `maven-publish`
}

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

tasks.register("sourcesJar", Jar::class) {
    archiveClassifier.set("sources")
    from(sourceSets["main"].allSource)
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])

            artifact(tasks["javadocJar"])
            artifact(tasks["sourcesJar"])
        }
    }
}