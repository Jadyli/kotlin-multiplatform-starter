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
                "io.github.jadyli.config-plugin" -> {
                    useModule("io.github.jadyli:config-plugin:${requested.version}")
                }
                "com.vanniktech.maven.publish" -> {
                    useModule("com.vanniktech.maven.publish:com.vanniktech.maven.publish.gradle.plugin:${requested.version}")
                }
                // "com.android.application" -> {
                //     useModule("com.android.tools.build:gradle:${requested.version}")
                // }
                // "com.android.library" -> {
                //     useModule("com.android.tools.build:gradle:${requested.version}")
                // }
                // "org.gradle.kotlin.kotlin-dsl" -> {
                //     useModule("org.gradle.kotlin:gradle-kotlin-dsl-plugins:${requested.version}")
                // }
                // "org.jetbrains.kotlin.android" -> {
                //     useModule("org.jetbrains.kotlin:kotlin-gradle-plugin:${requested.version}")
                // }
                // "org.jetbrains.kotlin.jvm" -> {
                //     useModule("org.jetbrains.kotlin:kotlin-gradle-plugin:${requested.version}")
                // }
                "com.google.devtools.ksp" -> {
                    useModule("com.google.devtools.ksp:symbol-processing-gradle-plugin:${requested.version}")
                }
            }
        }
    }
}

dependencyResolutionManagement {
    versionCatalogs {
        create("bizLibs") { from(files("${rootDir.path}/../.config/dependencies-biz.toml")) }
        create("androidCommonLibs") { from(files("${rootDir.path}/../.config/dependencies-android-common.toml")) }
        create("sharedCommonLibs") { from(files("${rootDir.path}/../.config/dependencies-shared-common.toml")) }
    }
}

rootProject.name = "feature"

include(
    ":startup",
    ":main-ui"
)
