package com.jady.feature.startup

import com.jady.lib.framework.http.HttpModule
import org.koin.ksp.generated.module

actual fun getCommonModules() = listOf(HttpModule().module, StartupModule().module)
