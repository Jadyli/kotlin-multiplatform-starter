@file:Suppress("UnstableApiUsage")

import com.jady.lib.config.ConfigExtension
import com.vanniktech.maven.publish.MavenPublishBaseExtension

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
    alias(sharedCommonLibs.plugins.compose) apply false
    alias(androidCommonLibs.plugins.config.plugin) apply false
    alias(sharedCommonLibs.plugins.maven.publish) apply false
}

allprojects {
    repositories {
        mavenCentral()
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
    group = "com.jady.lib.framework"
    apply(plugin = androidLibs.plugins.config.plugin.get().pluginId)
    apply(plugin = sharedLibs.plugins.maven.publish.get().pluginId)

    configure<ConfigExtension> {
        version {
            minSdk = androidLibs.versions.minSdk.get().toInt()
            targetSdk = androidLibs.versions.targetSdk.get().toInt()
            compileSdk = androidLibs.versions.compileSdk.get().toInt()
            java = androidLibs.versions.java.asProvider().get().toInt()
            kotlin = sharedLibs.versions.kotlin.asProvider().get()
            composePluginCompiler = sharedLibs.versions.compose.plugin.compiler.get()
            composeAndroidxCompiler = sharedLibs.versions.compose.androidx.compiler.get()
        }
        vectorDrawableSupportLibrary = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    configure<PublishingExtension> {
        repositories {
            maven {
                name = "XXX"
                url = uri("https://xxx")
            }
        }
    }
    configure<MavenPublishBaseExtension> {
        val moduleVersion = requireNotNull(businessLibs.framework.create("framework.$name").get().version)
        version = moduleVersion
        println("group: $group, name: $name, version: $moduleVersion")
        pom {
            name.set(project.name)
            description.set("Framework module.")
            url.set("https://github.com/Jadyli/kotlin-multiplatform-starter")
            scm {
                connection.set("scm:git:https://github.com/Jadyli/kotlin-multiplatform-starter.git")
                developerConnection.set("scm:git:ssh://git@github.com:Jadyli/kotlin-multiplatform-starter.git")
                url.set("https://github.com/Jadyli/kotlin-multiplatform-starter")
            }
            developers {
                developer {
                    name.set("XXX, Inc.")
                }
            }
        }
    }
}

val taskGroup = "custom"

tasks.register("publishAllModule") {
    group = taskGroup
    // setDependsOn(subprojects.mapNotNull { it.tasks.findByName("publishAllPublicationsToXXXRepository") })
    setDependsOn(subprojects.mapNotNull { it.tasks.findByName("publishToMavenLocal") })
}
