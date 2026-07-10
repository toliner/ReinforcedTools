import org.gradle.api.tasks.compile.JavaCompile
import org.gradle.api.tasks.javadoc.Javadoc
import org.gradle.jvm.tasks.Jar

plugins {
    id("multiloader-common")
}

configurations.maybeCreate("commonJava").apply {
    isCanBeResolved = true
}
configurations.maybeCreate("commonResources").apply {
    isCanBeResolved = true
}
configurations.maybeCreate("commonGeneratedResources").apply {
    isCanBeResolved = true
}

dependencies {
    compileOnly(project(":common"))
    add("commonJava", project(mapOf("path" to ":common", "configuration" to "commonJava")))
    add("commonResources", project(mapOf("path" to ":common", "configuration" to "commonResources")))
    add("commonGeneratedResources", project(mapOf("path" to ":common", "configuration" to "commonGeneratedResources")))
}

tasks.named<JavaCompile>("compileJava") {
    dependsOn(configurations.getByName("commonJava"))
    source(configurations.getByName("commonJava"))
}

tasks.named<ProcessResources>("processResources") {
    dependsOn(configurations.getByName("commonResources"))
    dependsOn(configurations.getByName("commonGeneratedResources"))
    from(configurations.getByName("commonResources"))
    from(configurations.getByName("commonGeneratedResources"))
}

tasks.named<Javadoc>("javadoc") {
    dependsOn(configurations.getByName("commonJava"))
    source(configurations.getByName("commonJava"))
}

tasks.named<Jar>("sourcesJar") {
    dependsOn(configurations.getByName("commonJava"))
    from(configurations.getByName("commonJava"))
    dependsOn(configurations.getByName("commonResources"))
    from(configurations.getByName("commonResources"))
    dependsOn(configurations.getByName("commonGeneratedResources"))
    from(configurations.getByName("commonGeneratedResources"))
}
