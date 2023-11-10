@file:Suppress("UnstableApiUsage")
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        maven { setUrl("https://jitpack.io") }
        google()
        maven { setUrl("https://maven.pkg.jetbrains.space/public/p/compose/dev") }
        maven { setUrl("https://maven.pkg.jetbrains.space/kotlin/p/wasm/experimental") }
        maven { setUrl("https://maven.pkg.jetbrains.space/kotlin/p/kotlin/dev") }
    }
    resolutionStrategy {
        eachPlugin {
            when (requested.id.id) {
                "com.android.application" -> {
                    useModule("com.android.tools.build:gradle:${requested.version}")
                }
                "org.jetbrains.kotlin.android" -> {
                    useModule("org.jetbrains.kotlin:kotlin-gradle-plugin:${requested.version}")
                }
                "io.github.jadyli.config-plugin" -> {
                    useModule("io.github.jadyli:config-plugin:${requested.version}")
                }
                "com.vanniktech.maven.publish" -> {
                    useModule("com.vanniktech.maven.publish:com.vanniktech.maven.publish.gradle.plugin:${requested.version}")
                }
                "com.google.devtools.ksp" -> {
                    useModule("com.google.devtools.ksp:symbol-processing-gradle-plugin:${requested.version}")
                }
            }
        }
    }
}

dependencyResolutionManagement {
    versionCatalogs {
        create("bizLibs") { from(files("${rootDir.path}/.config/dependencies-biz.toml")) }
        create("androidCommonLibs") { from(files("${rootDir.path}/.config/dependencies-android-common.toml")) }
        create("sharedCommonLibs") { from(files("${rootDir.path}/.config/dependencies-shared-common.toml")) }
        create("desktopLibs") { from(files("${rootDir.path}/.config/dependencies-desktop.toml")) }
    }
}

rootProject.name = "kotlin-multiplatform-starter"

include(":app")

includeBuild("framework")
includeBuild("feature")
include(":desktopApp")
