/*
 * Copyright (c) 2015-2023 BiliBili Inc.
 */

package com.missevan.feature.startup.config

import com.missevan.lib.framework.willow.BaseAndroidWillowConfig
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*

/**
 * @author jady
 * @since 2023/10/18 16/01
 * email: 1257984872@qq.com
 */
@Factory
class WillowConfig : BaseAndroidWillowConfig() {
    override fun config(config: HttpClientConfig<OkHttpConfig>) {
    }
}
