repositories {
    maven("https://s01.oss.sonatype.org/content/repositories/snapshots/")
}

dependencies {
    "implementation"(project(":socialismus-common-api"))
    "implementation"(project(":socialismus-shared"))
    "implementation"(libs.libby.core)
    
    "compileOnly"(libs.bundles.adventure)
}