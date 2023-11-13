@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(androidCommonLibs.plugins.android.application)
    alias(sharedCommonLibs.plugins.kotlin.android)
    alias(sharedCommonLibs.plugins.compose)
}

android {
    namespace = "com.jady.kotlin.multiplatform"

    defaultConfig {
        applicationId = "com.jady.kmp.demo"
        versionCode = 1
        versionName = "1.0"
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
