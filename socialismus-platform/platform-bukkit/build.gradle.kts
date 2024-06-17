import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

tasks.withType<ShadowJar> {
    archiveClassifier.set("BUKKIT")
}

dependencies {
    "compileOnly"(libs.bundles.bukkit)
    "implementation"(libs.libbyBukkit)
}