package com.jady.feature.startup

import com.jady.lib.framework.http.HttpModule
import kotlinx.coroutines.CoroutineScope
import org.koin.core.KoinApplication
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.ksp.generated.module

/**
 * @author jady
 * @since 2023/11/13 11:13
 * email: 1257984872@qq.com
 */
expect class StartUp : CoroutineScope {
    fun initLibs()
}

@Module
@ComponentScan
class StartupModule

fun KoinApplication.addCommonModules() {
    modules(
        HttpModule().module,
        StartupModule().module
    )
}
