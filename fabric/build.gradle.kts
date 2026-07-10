plugins {
    id("multiloader-loader")
    alias(libs.plugins.fabricLoom)
}

dependencies {
    minecraft(libs.minecraft)
    implementation(libs.fabricLoader)
    implementation(libs.fabricApi)
    implementation(libs.balmFabric) {
        isChanging = libs.versions.balm.get().contains("SNAPSHOT")
    }
}

apply(from = rootProject.file("repositories.gradle.kts"))

loom {
    runs {
        named("client") {
            client()
            setConfigName("Fabric Client")
            ideConfigGenerated(true)
            runDir("runs/client")
        }
        named("server") {
            server()
            setConfigName("Fabric Server")
            ideConfigGenerated(true)
            runDir("runs/server")
        }
    }
}
