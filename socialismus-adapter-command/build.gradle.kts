repositories {
    maven("https://jitpack.io")
}

dependencies {
    "implementation"(project(":socialismus-common-api"))

    "compileOnly"(libs.bundles.cloud)
    "compileOnly"(libs.cloudBukkit)
    "compileOnly"(libs.cloudPaper)
    "compileOnly"(libs.cloudVelocity)
}