/*
 * Copyright (c) 2015-2023 BiliBili Inc.
 */

package com.jady.feature.startup.config

import com.jady.lib.framework.http.BaseAndroidHttpConfig
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import okhttp3.Protocol
import org.koin.core.annotation.Factory
import java.util.concurrent.TimeUnit

/**
 * @author jady
 * @since 2023/10/18 16/01
 * email: 1257984872@qq.com
 */
@Factory
class WillowConfig : BaseAndroidHttpConfig() {
    override fun HttpClientConfig<OkHttpConfig>.config() {
        engine {
            config {
            }
        }
    }
}

fun initWillow() {
}
