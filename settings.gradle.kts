pluginManagement {
    plugins {
        id("com.google.devtools.ksp") version "1.8.20-1.0.11"
        kotlin("jvm") version "1.8.20"
        id("com.android.library") version "8.0.0"
        id("org.jetbrains.kotlin.android") version "1.8.20"
    }

    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "KotlinCompilerPluginSample"
include(":app")
include(":annotations")
include(":processor")
