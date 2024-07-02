@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(androidCommonLibs.plugins.android.application)
    alias(sharedCommonLibs.plugins.kotlin.android)
    alias(sharedCommonLibs.plugins.jetbrains.compose)
    alias(sharedCommonLibs.plugins.compose.compiler)
}

android {
    namespace = "com.jady.kotlin.multiplatform"

    defaultConfig {
        applicationId = "com.jady.kmp.demo"
        versionCode = 2
        versionName = "1.0"
    }
    signingConfigs {
        create("release") {
            storeFile = project.file("keystore.jks")
            storePassword = "123456"
            keyAlias = "key"
            keyPassword = "123456"
        }
    }
}

dependencies {
    implementation(androidCommonLibs.androidx.core.ktx)
    implementation(androidCommonLibs.androidx.lifecycle.runtime.ktx)
    implementation(androidCommonLibs.androidx.activity.compose)
    implementation(compose.ui)
    implementation(compose.uiTooling)
    implementation(compose.material3)
    implementation(bizLibs.feature.startup)
    implementation(bizLibs.feature.main.ui)
    testImplementation(androidCommonLibs.junit)
    androidTestImplementation(androidCommonLibs.androidx.test.junit)
    androidTestImplementation(androidCommonLibs.espresso.core)
    @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
    androidTestImplementation(compose.uiTestJUnit4)
}
