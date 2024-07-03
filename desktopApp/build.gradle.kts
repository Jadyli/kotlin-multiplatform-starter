import org.jetbrains.compose.desktop.application.dsl.TargetFormat

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(sharedCommonLibs.plugins.kotlin.multiplatform)
    alias(sharedCommonLibs.plugins.jetbrains.compose)
    alias(sharedCommonLibs.plugins.compose.compiler)
    alias(sharedCommonLibs.plugins.ksp)
}

kotlin {
    jvm("desktop")
    sourceSets {
        commonMain {
            dependencies {
                api(bizLibs.framework.http)
                api(bizLibs.feature.main.ui)
                api(bizLibs.feature.startup)
            }
        }
        val desktopMain by getting {
            dependencies {
                implementation(compose.desktop.currentOs)
                implementation(desktopLibs.logback.classic)
            }
        }
    }
}

compose.desktop {
    application {
        mainClass = "com.jady.kmp.demo.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "com.jady.kmp.demo"
            packageVersion = "1.0.0"
        }
    }
}
