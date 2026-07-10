val modId = providers.gradleProperty("mod_id").get()

plugins {
    id("multiloader-loader")
    alias(libs.plugins.modDevGradle)
}

neoForge {
    version = libs.neoForge.get().version

    runs {
        configureEach {
            systemProperty("neoforge.enabledGameTestNamespaces", modId)
            ideName = "NeoForge ${name.replaceFirstChar { it.uppercase() }} (${project.path})"
        }
        create("client") {
            client()
        }
        create("server") {
            server()
        }
    }

    mods {
        create(modId) {
            sourceSet(sourceSets.getByName("main"))
        }
    }
}

sourceSets.named("main") {
    resources.srcDir("src/generated/resources")
}

apply(from = rootProject.file("repositories.gradle.kts"))

dependencies {
    implementation(libs.balmNeoForge) {
        isChanging = libs.versions.balm.get().contains("SNAPSHOT")
    }
}

val neoForgeSnapshotUrl = providers.gradleProperty("neoforge_snapshot_url").orNull
if (!neoForgeSnapshotUrl.isNullOrBlank()) {
    repositories {
        maven {
            name = "NeoForge Snapshots"
            url = uri(neoForgeSnapshotUrl)
            content {
                includeModule("net.neoforged", "neoforge")
                includeModule("net.neoforged", "testframework")
            }
        }
    }
}
