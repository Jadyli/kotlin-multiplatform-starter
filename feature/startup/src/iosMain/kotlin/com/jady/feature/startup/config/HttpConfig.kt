/*
 * Copyright (c) 2015-2023 BiliBili Inc.
 */

package com.jady.feature.startup.config

import com.jady.lib.framework.http.BaseIosHttpConfig
import io.ktor.client.*
import io.ktor.client.engine.darwin.*
import org.koin.core.annotation.Factory

/**
 * @author jady
 * @since 2023/10/18 16/01
 * email: 1257984872@qq.com
 */
@Factory(binds = [BaseIosHttpConfig::class])
class HttpConfig : BaseIosHttpConfig() {
    override fun HttpClientConfig<DarwinClientEngineConfig>.config() {
    }
}
