subprojects {
    dependencies {
        "implementation"(project(":socialismus-common-api"))

        "compileOnly"(rootProject.libs.bundles.adventure)
    }
}