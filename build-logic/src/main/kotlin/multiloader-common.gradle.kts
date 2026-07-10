import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.jvm.tasks.Jar

plugins {
    id("java-library")
    id("maven-publish")
}

val configuredJavaVersion = 25
val modId = providers.gradleProperty("mod_id").get()
val modName = providers.gradleProperty("mod_name").get()
val modAuthor = providers.gradleProperty("mod_author").get()
val licenseName = providers.gradleProperty("license").get()
val homepage = providers.gradleProperty("homepage").get()
val discord = providers.gradleProperty("discord").get()
val issues = providers.gradleProperty("issues").get()
val sources = providers.gradleProperty("sources").get()
val credits = providers.gradleProperty("credits").get()
val catalog = project.extensions.getByType<VersionCatalogsExtension>().named("libs")
val minecraftVersion = catalog.findVersion("minecraft").get().requiredVersion
val fabricApiVersion = catalog.findVersion("fabricApi").get().requiredVersion
val fabricLoaderVersion = catalog.findVersion("fabricLoader").get().requiredVersion
val neoForgeVersion = catalog.findVersion("neoForge").get().requiredVersion
val balmVersion = catalog.findVersion("balm").get().requiredVersion

base {
    archivesName.set("${modId}-${project.name}-${minecraftVersion}")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(configuredJavaVersion))
    withSourcesJar()
    withJavadocJar()
}

repositories {
    mavenCentral()
    exclusiveContent {
        forRepository {
            maven {
                name = "Sponge"
                url = uri("https://repo.spongepowered.org/repository/maven-public")
            }
        }
        filter {
            includeGroupAndSubgroups("org.spongepowered")
        }
    }
    exclusiveContent {
        forRepositories(
            maven {
                name = "ParchmentMC"
                url = uri("https://maven.parchmentmc.org/")
            },
            maven {
                name = "NeoForge"
                url = uri("https://maven.neoforged.net/releases")
            },
        )
        filter {
            includeGroup("org.parchmentmc.data")
        }
    }
}

listOf("apiElements", "runtimeElements", "sourcesElements", "javadocElements").forEach { variant ->
    publishing.publications.withType<MavenPublication>().configureEach {
        suppressPomMetadataWarningsFor(variant)
    }
}

tasks.named<Jar>("sourcesJar") {
    from(rootProject.file("LICENSE")) {
        rename { "${it}_${modName}" }
    }
}

tasks.named<Jar>("jar") {
    from(rootProject.file("LICENSE")) {
        rename { "${it}_${modName}" }
    }

    manifest {
        attributes(
            mapOf(
                "Specification-Title" to modName,
                "Specification-Vendor" to modAuthor,
                "Specification-Version" to archiveVersion.get(),
                "Implementation-Title" to project.name,
                "Implementation-Version" to archiveVersion.get(),
                "Implementation-Vendor" to modAuthor,
                "Built-On-Minecraft" to minecraftVersion,
            ),
        )
    }
}

tasks.named<ProcessResources>("processResources") {
    val minecraftVersionMatch = Regex("""^(\d+)\.(\d+)""").find(minecraftVersion)
        ?: error("Unsupported Minecraft version format: $minecraftVersion")
    val minecraftMajorMinor = "${minecraftVersionMatch.groupValues[1]}.${minecraftVersionMatch.groupValues[2]}"
    val nextMinecraftMinor = "${minecraftVersionMatch.groupValues[1]}.${minecraftVersionMatch.groupValues[2].toInt() + 1}"
    val expandProps: Map<String, Any> = mapOf(
        "version" to project.version,
        "group" to project.group,
        "minecraft_version_range" to "[$minecraftMajorMinor-,$nextMinecraftMinor)",
        "fabric_minecraft_version_range" to "~$minecraftMajorMinor-",
        "fabric_api_version" to fabricApiVersion,
        "fabric_loader_version" to fabricLoaderVersion,
        "mod_name" to modName,
        "mod_author" to modAuthor,
        "mod_id" to modId,
        "license" to licenseName,
        "description" to (project.description ?: ""),
        "neoforge_version" to neoForgeVersion,
        "credits" to credits,
        "java_version" to configuredJavaVersion,
        "homepage" to homepage,
        "discord" to discord,
        "issues" to issues,
        "sources" to sources,
        "balm_version" to balmVersion.substringBefore("+"),
    )

    val jsonExpandProps = expandProps.mapValues { (_, value) ->
        if (value is String) value.replace("\n", "\\\\n") else value
    }

    filesMatching(listOf("META-INF/neoforge.mods.toml")) {
        expand(expandProps)
    }
    filesMatching(listOf("fabric.mod.json")) {
        expand(jsonExpandProps)
    }
    inputs.properties(expandProps)
}

publishing {
    publications {
        register<MavenPublication>("mavenJava") {
            version = project.version.toString()
            artifactId = "${modId.replace('_', '-')}-${project.name}"
            from(components["java"])
        }
    }
}
