/*
 * Copyright (c) 2015-2023 BiliBili Inc.
 */

package com.jady.lib.framework.http

import com.jady.lib.framework.http.config.BaseHttpConfig
import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.client.engine.okhttp.*
import org.koin.mp.KoinPlatform

/**
 * @author jady
 * @since 2023/09/08 17/02
 * email: 1257984872@qq.com
 */
actual fun createClient(commonConfig: HttpClientConfig<out HttpClientEngineConfig>.(BaseHttpConfig<*>) -> Unit): HttpClient {
    val httpConfig = KoinPlatform.getKoin().get<BaseOkHttpHttpConfig>()
    return HttpClient(OkHttp) {
        commonConfig(httpConfig)
        with(httpConfig) {
            config()
        }
    }
}

abstract class BaseOkHttpHttpConfig : BaseHttpConfig<OkHttpConfig>()
