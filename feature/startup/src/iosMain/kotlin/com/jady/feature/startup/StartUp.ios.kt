package com.jady.feature.startup

import com.jady.lib.framework.http.HttpModule

actual actual fun getCommonModules() = listOf(HttpModule().module, StartupModule().module)
