import com.jady.lib.config.ConfigExtension

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
val sharedLibs = sharedCommonLibs
subprojects {
    apply(plugin = androidLibs.plugins.config.plugin.get().pluginId)

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
}

val taskGroup = "custom"

tasks.create("publishAllModule") {
    group = taskGroup
    dependsOn(
        gradle.includedBuilds
            .filter { it.name != "plugin" }
            .mapNotNull { it.task(":publishAllModule") }
    )
}
