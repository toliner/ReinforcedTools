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

artifacts {
    add("commonJava", sourceSets.getByName("main").java.sourceDirectories.singleFile)
    add("commonResources", sourceSets.getByName("main").resources.sourceDirectories.singleFile)
    add("commonGeneratedResources", sourceSets.getByName("generated").resources.sourceDirectories.singleFile)
}
