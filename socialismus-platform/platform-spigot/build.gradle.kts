import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

tasks.withType<ShadowJar> {
    archiveClassifier.set("SPIGOT")
}

dependencies {
    "compileOnly"(libs.bundles.spigot)
    "implementation"("com.saicone.ezlib:ezlib:1.2.3")
}