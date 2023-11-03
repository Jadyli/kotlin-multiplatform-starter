/*
 * Copyright (c) 2015-2023 BiliBili Inc.
 */

package com.jady.feature.startup.config

import cn.missevan.lib.utils.LogLevel
import cn.missevan.lib.utils.initLogs
import cn.missevan.lib.utils.toSeverity
import co.touchlab.kermit.Logger

/**
 * @author jady
 * @since 2023/11/02 16:18
 * email: 1257984872@qq.com
 */
actual fun initLog() {
    initLogs(
        logAction = { priority: LogLevel, tag: String?, msg: String? ->
            Logger.log(priority.toSeverity(), tag ?: "", throwable = null, message = msg ?: "")
        },
        reportAction = { msg: String?, throwable: Throwable, sampleRate: Float ->
        }
    )
}
