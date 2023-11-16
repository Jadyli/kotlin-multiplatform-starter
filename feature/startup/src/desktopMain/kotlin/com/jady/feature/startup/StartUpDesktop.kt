package com.jady.feature.startup

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.core.context.startKoin

/**
 * @author jady
 * @since 2023/11/13 11:19
 * email: 1257984872@qq.com
 */
actual class StartUp : CoroutineScope by CoroutineScope(SupervisorJob() + Dispatchers.Default) {
    actual fun initLibs() {
    }
}
