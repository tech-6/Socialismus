repositories {
    maven("https://jitpack.io")
}

dependencies {
    "implementation"(project(":socialismus-common-api"))
    "implementation"(project(":socialismus-shared"))

    "compileOnly"(libs.bundles.cloud)
    "compileOnly"(libs.cloud.paper)
    "compileOnly"(libs.cloud.velocity)
    "annotationProcessor"(libs.cloud.core)
}