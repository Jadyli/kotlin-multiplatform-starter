/*
 * Copyright (c) 2015-2023 BiliBili Inc.
 */

package com.jady.lib.framework.http

import com.jady.lib.framework.http.config.BaseHttpConfig
import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.compression.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.plugins.resources.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Module

/**
 * @author jady
 * @since 2023/09/08 17/02
 * email: 1257984872@qq.com
 */
@Module
@ComponentScan
class HttpModule

@Factory
internal fun createHttpClient(): HttpClient = createClient { willowConfig ->
    defaultRequest {
        header(HttpHeaders.ContentType, ContentType.Application.Json)
    }
    install(HttpTimeout) {
        requestTimeoutMillis = willowConfig.readTimeOut
        connectTimeoutMillis = willowConfig.connectTimeOut
    }
    ContentEncoding()
    install(Resources)
    install(Logging) {
        logger = object : Logger {
            override fun log(message: String) {
                println(message)
            }
        }
        level = LogLevel.HEADERS
    }
    install(ContentNegotiation) {
        json(
            Json {
                prettyPrint = true
                isLenient = true
            }
        )
    }
}

internal expect fun createClient(commonConfig: HttpClientConfig<out HttpClientEngineConfig>.(BaseHttpConfig<*>) -> Unit): HttpClient
