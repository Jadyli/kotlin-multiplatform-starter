package com.jady.feature.startup

import kotlinx.coroutines.CoroutineScope
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module

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

expect fun getCommonModules(): List<org.koin.core.module.Module>
