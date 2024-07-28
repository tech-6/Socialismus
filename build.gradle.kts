import org.gradle.api.tasks.Copy
import java.util.Properties

val version = "2.0.0"

defaultTasks("build", "shadowJar")

allprojects {
    apply(plugin = "java")

    tasks.withType<JavaCompile> {
        sourceCompatibility = JavaVersion.VERSION_21.toString()
        targetCompatibility = JavaVersion.VERSION_21.toString()
    }
}

subprojects {
    repositories {
        mavenCentral()
    }

    dependencies {
        // lombok
        "compileOnly"(rootProject.libs.lombok)
        "annotationProcessor"(rootProject.libs.lombok)

        // general
        "compileOnly"(rootProject.libs.guice)
    }
}