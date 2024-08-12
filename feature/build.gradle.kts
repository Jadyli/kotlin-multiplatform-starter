@file:Suppress("UnstableApiUsage")

import com.jady.lib.config.CommonConfigExtension
import com.jady.lib.config.MavenRepository
import com.jady.lib.config.PomExtension
import com.jady.lib.config.applyLibResPlugin
import com.jady.lib.config.applyMavenPlugin

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(androidCommonLibs.plugins.android.application) apply false
    alias(androidCommonLibs.plugins.android.library) apply false
    alias(sharedCommonLibs.plugins.kotlin.jvm) apply false
    alias(sharedCommonLibs.plugins.kotlin.android) apply false
    alias(sharedCommonLibs.plugins.kotlin.serialization) apply false
    alias(sharedCommonLibs.plugins.kotlin.parcelize) apply false
    alias(sharedCommonLibs.plugins.kotlin.multiplatform) apply false
    alias(sharedCommonLibs.plugins.kotlin.native.cocoapods) apply false
    alias(sharedCommonLibs.plugins.ksp) apply false
    alias(sharedCommonLibs.plugins.jetbrains.compose) apply false
    alias(sharedCommonLibs.plugins.compose.compiler) apply false
    alias(sharedCommonLibs.plugins.config.plugin) apply false
    alias(sharedCommonLibs.plugins.maven.publish) apply false
}

allprojects {
    repositories {
        mavenCentral()
        maven { setUrl("https://maven.aliyun.com/repository/public") }
        maven { setUrl("https://maven.aliyun.com/repository/google") }
        maven { setUrl("https://jitpack.io") }
        google()
        maven { setUrl("https://maven.pkg.jetbrains.space/public/p/compose/dev") }
        maven { setUrl("https://maven.pkg.jetbrains.space/kotlin/p/wasm/experimental") }
        maven { setUrl("https://maven.pkg.jetbrains.space/kotlin/p/kotlin/dev") }
    }
}

val androidLibs = androidCommonLibs
val businessLibs = bizLibs
val sharedLibs = sharedCommonLibs
subprojects {
    group = "com.jady.feature"
    version = requireNotNull(businessLibs.feature.create("feature.$name").get().version)
    apply(plugin = sharedLibs.plugins.config.plugin.get().pluginId)
    apply(plugin = sharedLibs.plugins.maven.publish.get().pluginId)

    configure<CommonConfigExtension> {
        version {
            minSdk = businessLibs.versions.minSdk.get().toInt()
            targetSdk = businessLibs.versions.targetSdk.get().toInt()
            compileSdk = androidLibs.versions.compileSdk.get().toInt()
            java = androidLibs.versions.java.asProvider().get().toInt()
            kotlin = sharedLibs.versions.kotlin.asProvider().get()
        }
        vectorDrawableSupportLibrary = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    project.applyLibResPlugin {
        generatedClassName = "LibRes"
        generateNamedArguments = true
        baseLocaleLanguageCode = "zh"
        camelCaseNamesForAppleFramework = false
    }
    project.applyMavenPlugin {
        mavenRepository = MavenRepository(
            name = "XXX",
            releaseUrl = "https://xxx",
            snapshotUrl = "https://xxx"
        )
        pom = PomExtension(
            repoUrl = "https://github.com/Jadyli/kotlin-multiplatform-starter",
            httpsConnection = "scm:git:https://github.com/Jadyli/kotlin-multiplatform-starter.git",
            gitConnection = "scm:git:ssh://git@github.com:Jadyli/kotlin-multiplatform-starter.git",
            developerName = "XXX, Inc."
        )
    }
}

val taskGroup = "custom"

tasks.register("publishAllModule") {
    group = taskGroup
    setDependsOn(subprojects.mapNotNull { it.tasks.findByName("publishAllPublicationsToXXXRepository") })
}
