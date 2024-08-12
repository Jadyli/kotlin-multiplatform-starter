@file:Suppress("UnstableApiUsage", "OPT_IN_IS_NOT_ENABLED", "OPT_IN_USAGE")

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
    alias(sharedCommonLibs.plugins.kotlin.serialization)
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
                api(sharedCommonLibs.kotlinx.serialization)
                api(sharedCommonLibs.ktor.client.core)
                api(sharedCommonLibs.ktor.client.auth)
                api(sharedCommonLibs.ktor.serialization.kotlinx.json)
                api(sharedCommonLibs.ktor.client.content.negotiation)
                api(sharedCommonLibs.ktor.client.resources)
                api(sharedCommonLibs.ktor.client.logging)
                api(sharedCommonLibs.ktor.client.encoding)
                api(sharedCommonLibs.koin.core)
                api(sharedCommonLibs.koin.annotations)
            }
        }
        val jvmCommonMain by getting {
            dependencies {
                api(sharedCommonLibs.ktor.client.okhttp.get().toString()) {
                    exclude(group = "com.squareup.okhttp3")
                }
            }
        }
        val androidMain by getting {
            dependencies {
                api(androidCommonLibs.androidx.core.ktx)
                api(compose.uiTooling)
                api(sharedCommonLibs.koin.android)
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
        // val jsWasmMain by creating {
        //     dependsOn(commonMain.get())
        // }
        // jsMain {
        //     dependsOn(jsWasmMain)
        // }
        // val wasmJsMain by getting {
        // }
    }
}

android {
    namespace = "com.jady.lib.framework.http"
}

dependencies {
    addKspDependencies(listOf(KspCompiler(true, true, sharedCommonLibs.koin.ksp.compiler)))
}

ksp {
    configKspExtension()
}
