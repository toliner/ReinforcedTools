plugins {
    id("multiloader-common")
    alias(libs.plugins.modDevGradle)
}

neoForge {
    neoFormVersion = libs.neoForm.get().version
}

dependencies {
    implementation(libs.balmCommon) {
        isChanging = libs.versions.balm.get().endsWith("SNAPSHOT")
    }
    accessTransformers(libs.balmCommon)
}

apply(from = rootProject.file("repositories.gradle.kts"))

configurations.maybeCreate("commonJava").apply {
    isCanBeResolved = false
    isCanBeConsumed = true
}
configurations.maybeCreate("commonResources").apply {
    isCanBeResolved = false
    isCanBeConsumed = true
}
configurations.maybeCreate("commonGeneratedResources").apply {
    isCanBeResolved = false
    isCanBeConsumed = true
}

sourceSets {
    create("generated") {
        resources.srcDir("src/generated/resources")
    }
}

val generatedResourcesDirectory = layout.buildDirectory.dir("generated-resources")
val generateReinforcedToolsData = tasks.register<JavaExec>("generateReinforcedToolsData") {
    group = "data generation"
    description = "Generates Reinforced Tools recipes, models, tags, and translations."
    dependsOn(tasks.named("classes"))
    classpath = sourceSets.getByName("main").runtimeClasspath
    mainClass.set("dev.toliner.reinforcedtools.ReinforcedToolsDataGenerator")
    args(generatedResourcesDirectory.get().asFile.absolutePath)
}

artifacts {
    add("commonJava", sourceSets.getByName("main").java.sourceDirectories.singleFile)
    add("commonResources", sourceSets.getByName("main").resources.sourceDirectories.singleFile)
    add("commonGeneratedResources", generatedResourcesDirectory) {
        builtBy(generateReinforcedToolsData)
    }
}
