package com.jady.kmp.demo

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.jady.feature.main.ui.CounterScreen
import com.jady.feature.startup.StartUp
import com.jady.feature.startup.getCommonModules
import org.koin.core.context.startKoin

@Composable
@Preview
fun App() {
    MaterialTheme {
        CounterScreen()
    }
}

fun main() = application {
    StartUp().initLibs()
    startKoin {
        modules(getCommonModules())
    }
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
