repositories {
    maven("https://jitpack.io")
}

dependencies {
    "implementation"(project(":socialismus-common-api"))

    "compileOnly"(rootProject.libs.valiobungee)
}