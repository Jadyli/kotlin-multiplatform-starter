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
    group = "com.jady.feature"
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
        val moduleVersion = requireNotNull(businessLibs.feature.create("feature.$name").get().version)
        version = moduleVersion
        println("group: $group, name: $name, version: $moduleVersion")
        pom {
            name.set(project.name)
            description.set("Feature module.")
            url.set("https://git.xxx/KMP-Demo")
            scm {
                connection.set("scm:git:https://git.xxx/KMP-Demo.git")
                developerConnection.set("scm:git:ssh://git@git.xxx/KMP-Demo.git")
                url.set("https://git.xxx/KMP-Demo")
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
    setDependsOn(subprojects.mapNotNull { it.tasks.findByName("publishAllPublicationsToXXXRepository") })
}
