import org.gradle.api.tasks.Copy
import java.util.Properties

val socialismusVersion = "2.0.0"

defaultTasks("build", "shadowJar")

allprojects {
    version = socialismusVersion

    apply(plugin = "java")
}

tasks.withType<JavaCompile>().configureEach {
  sourceCompatibility = JavaVersion.VERSION_17.toString()
  targetCompatibility = JavaVersion.VERSION_17.toString()
}

subprojects {
    repositories {
        mavenCentral()
    }

    dependencies {
        // lombok
        "compileOnly"(rootProject.libs.lombok)
        "annotationProcessor"(rootProject.libs.lombok)
    }
}