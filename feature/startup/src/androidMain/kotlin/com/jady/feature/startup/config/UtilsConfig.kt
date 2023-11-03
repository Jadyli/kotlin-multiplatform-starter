/*
 * Copyright (c) 2015-2023 BiliBili Inc.
 */

package com.jady.feature.startup.config

import cn.missevan.lib.utils.LogLevel
import cn.missevan.lib.utils.initLogs
import cn.missevan.lib.utils.toLogLevel
import cn.missevan.library.utilities.sentry.captureException
import tv.danmaku.android.log.BLog

/**
 * @author jady
 * @since 2023/11/02 16:18
 * email: 1257984872@qq.com
 */
actual fun initLog() {
    initLogs(
        logAction = { priority: LogLevel, tag: String?, msg: String? ->
            BLog.log(priority.toLogLevel(), tag, msg)
        },
        reportAction = { msg: String?, throwable: Throwable, sampleRate: Float ->
            captureException(msg, throwable, sampleRate)
        }
    )
}
