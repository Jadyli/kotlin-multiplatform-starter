/*
 * Copyright (c) 2015-2023 BiliBili Inc.
 */

package com.jady.feature.startup.config

import com.jady.lib.framework.http.BaseDesktopHttpConfig
import com.jady.lib.framework.http.BaseOkHttpHttpConfig
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Singleton

/**
 * @author jady
 * @since 2023/10/18 16:01
 * email: 1257984872@qq.com
 */
@Factory(binds = [BaseOkHttpHttpConfig::class])
class WillowConfig : BaseDesktopHttpConfig() {
    override fun HttpClientConfig<OkHttpConfig>.config() {
        engine {
            config {
            }
        }
    }
}
