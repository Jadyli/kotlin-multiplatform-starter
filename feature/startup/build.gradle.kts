@file:Suppress("UnstableApiUsage")

import com.jady.lib.config.KspCompiler
import com.jady.lib.config.addKspDependencies
import com.jady.lib.config.configKMPPlugin
import com.jady.lib.config.configKspExtension

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(androidCommonLibs.plugins.android.library)
    alias(sharedCommonLibs.plugins.kotlin.multiplatform)
    alias(sharedCommonLibs.plugins.jetbrains.compose)
    alias(sharedCommonLibs.plugins.compose.compiler)
    alias(sharedCommonLibs.plugins.kotlin.native.cocoapods)
    alias(sharedCommonLibs.plugins.ksp)
}

kotlin {
    configKMPPlugin(
        project,
        androidCommonLibs.versions.java.asProvider().get().toInt(),
        true
    )

    // applyDefaultHierarchyTemplate()

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(bizLibs.framework.http)
                api(sharedCommonLibs.koin.core)
                api(sharedCommonLibs.koin.annotations)
            }
        }
        val jvmCommonMain by getting {
            dependencies {
                api(sharedCommonLibs.ktor.client.okhttp.get().toString())
            }
        }
        val androidMain by getting {
            dependencies {
                api(androidCommonLibs.androidx.annotation)
                api(androidCommonLibs.androidx.core.ktx)
                api(compose.uiTooling)
            }
        }
        val androidUnitTest by getting {
            dependencies {
                implementation(androidCommonLibs.androidx.test.runner)
                implementation(sharedCommonLibs.kotlin.test)
                implementation(sharedCommonLibs.kotlin.coroutines.test)
                implementation(androidCommonLibs.junit)
            }
        }
        val iosMain by getting {
            dependencies {
                api(sharedCommonLibs.ktor.client.darwin)
            }
        }
    }
}

android {
    namespace = "com.jady.feature.startup"
}

dependencies {
    addKspDependencies(listOf(KspCompiler(true, false, sharedCommonLibs.koin.ksp.compiler)))
}

ksp {
    configKspExtension()
}
