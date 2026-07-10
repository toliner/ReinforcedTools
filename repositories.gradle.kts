repositories {
    maven {
        name = "Twelve Iterations"
        url = uri("https://maven.twelveiterations.com/repository/maven-public/")
        content {
            includeGroup("net.blay09.mods")
        }
    }

    exclusiveContent {
        forRepository {
            maven {
                name = "Minecraft"
                url = uri("https://libraries.minecraft.net/")
            }
        }
        filter {
            includeGroupAndSubgroups("com.mojang")
        }
    }
}
