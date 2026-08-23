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
        create("gameTestServer") {
            server()
            mainClass.set("net.neoforged.fml.startup.GameTestServer")
            gameDirectory.set(layout.buildDirectory.dir("gametest-server"))
            systemProperty("neoforge.enableGameTest", "true")
            programArgument("--tests")
            programArgument("$modId:*")
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

val javaToolchains = project.extensions.getByType<JavaToolchainService>()
val java25Launcher = javaToolchains.launcherFor {
    languageVersion.set(JavaLanguageVersion.of(25))
}

tasks.configureEach {
    if (this.hasProperty("javaExecutable")) {
        try {
            val prop = this.property("javaExecutable")
            if (prop is RegularFileProperty) {
                prop.set(java25Launcher.map { it.executablePath })
            } else if (prop is Property<*>) {
                try {
                    @Suppress("UNCHECKED_CAST")
                    (prop as Property<String>).set(java25Launcher.map { it.executablePath.asFile.absolutePath })
                } catch (_: Exception) {
                    @Suppress("UNCHECKED_CAST")
                    (prop as Property<RegularFile>).set(java25Launcher.map { it.executablePath })
                }
            }
        } catch (_: Exception) {
        }
    }
    if (this.hasProperty("javaLauncher")) {
        try {
            val prop = this.property("javaLauncher")
            if (prop is Property<*>) {
                @Suppress("UNCHECKED_CAST")
                (prop as Property<JavaLauncher>).set(java25Launcher)
            }
        } catch (_: Exception) {
        }
    }
}
