@file:Suppress("UnstableApiUsage", "OPT_IN_IS_NOT_ENABLED", "OPT_IN_USAGE")

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(androidCommonLibs.plugins.android.library)
    alias(sharedCommonLibs.plugins.compose)
    alias(sharedCommonLibs.plugins.kotlin.multiplatform)
    alias(sharedCommonLibs.plugins.kotlin.native.cocoapods)
    alias(sharedCommonLibs.plugins.ksp)
    alias(sharedCommonLibs.plugins.kotlin.serialization)
}

kotlin {
    androidTarget()
    jvm("desktop")
    // wasmJs {
    //     browser()
    // }
    // wasmWasi() {
    //     nodejs()
    // }
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    cocoapods {
        version = "1.0.0"
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        ios.deploymentTarget = "14.1"
        podfile = file(rootDir.parentFile.path + "/iosApp/Podfile")
        framework {
            baseName = "main-ui"
        }
    }

    // applyDefaultHierarchyTemplate()

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(compose.runtime)
                api(compose.foundation)
                api(compose.material3)
                @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                api(compose.components.resources)
                api(sharedCommonLibs.ktor.client.core)
                api(sharedCommonLibs.ktor.client.auth)
                api(bizLibs.framework.http)
                api(sharedCommonLibs.ktor.client.content.negotiation)
                api(sharedCommonLibs.ktor.serialization.kotlinx.json)
                api(sharedCommonLibs.koin.core)
                api(sharedCommonLibs.koin.annotations)
                api(sharedCommonLibs.molecule)
            }
        }
        val jvmCommonMain by creating {
            dependsOn(commonMain)
            dependencies {
                api(sharedCommonLibs.ktor.client.okhttp.get().toString())
            }
        }
        val androidMain by getting {
            dependsOn(jvmCommonMain)
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
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
            dependencies {
                api(sharedCommonLibs.ktor.client.darwin)
            }
        }
        val desktopMain by getting {
            dependsOn(jvmCommonMain)
            dependencies {
                api(compose.desktop.common)
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
    namespace = "com.jady.feature.main.ui"
}

dependencies {
    with(sharedCommonLibs.koin.ksp.compiler.get().toString()) {
        add("kspCommonMainMetadata", this)
        add("kspDesktop", this)
        add("kspAndroid", this)
        add("kspIosX64", this)
        add("kspIosArm64", this)
        add("kspIosSimulatorArm64", this)
    }
}

ksp {
    arg("KOIN_CONFIG_CHECK", "true")
}
