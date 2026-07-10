plugins {
    alias(libs.plugins.fabricLoom) apply false
    alias(libs.plugins.modDevGradle) apply false
}

subprojects {
    configurations.configureEach {
        resolutionStrategy {
            cacheChangingModulesFor(60, "seconds")
            cacheDynamicVersionsFor(60, "seconds")
        }
    }
}
