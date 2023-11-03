/*
 * Copyright (c) 2015-2023 BiliBili Inc.
 */

package com.jady.lib.framework.http.config

import io.ktor.client.*
import io.ktor.client.engine.*

/**
 * @author jady
 * @since 2023/09/21 17/38
 * email: 1257984872@qq.com
 */
abstract class BaseHttpConfig<T : HttpClientEngineConfig> {
    open val protocols: ArrayList<Protocol> = arrayListOf(Protocol.HTTP_1_1)
    open var connectTimeOut: Long = 30_000L
    open var readTimeOut: Long = 30_000L

    abstract fun HttpClientConfig<T>.config()
}
