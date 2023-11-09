package com.jady.kmp.demo

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.jady.feature.main.ui.CounterScreen
import com.jady.feature.startup.StartupModule
import com.jady.lib.framework.http.HttpModule
import org.koin.core.context.startKoin
import org.koin.ksp.generated.*

@Composable
@Preview
fun App() {
    MaterialTheme {
        CounterScreen()
    }
}

fun main() = application {
    startKoin {
        modules(
            HttpModule().module,
            StartupModule().module
        )
    }
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
