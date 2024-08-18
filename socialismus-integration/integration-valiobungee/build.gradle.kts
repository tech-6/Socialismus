repositories {
    maven("https://jitpack.io")
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    "implementation"(project(":socialismus-common-api"))
    "implementation"(project(":socialismus-common"))

    "compileOnly"(rootProject.libs.velocity)
    "compileOnly"(rootProject.libs.valiobungee)
}