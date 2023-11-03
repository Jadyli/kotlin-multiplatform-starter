/*
 * Copyright (c) 2015-2023 BiliBili Inc.
 */

package com.jady.feature.startup.config

import cn.missevan.core.api.MissEvanCookieJar
import cn.missevan.core.api.RecordRequestTimeInterceptor
import cn.missevan.library.api.ApiConstants
import cn.missevan.library.api.cronet.api.CronetBridge
import cn.missevan.library.api.httpdns.MissEvanHttpDnsImpl
import cn.missevan.library.api.httpdns.internal.configs.HttpDnsConfig
import cn.missevan.library.api.interceptor.HeaderInterceptor
import cn.missevan.library.errorhandler.interceptor.RetryShootInterceptor
import cn.missevan.play.api.RequestSignInterceptor
import com.jady.lib.framework.willow.BaseAndroidWillowConfig
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.sentry.android.okhttp.SentryOkHttpInterceptor
import okhttp3.Protocol
import org.koin.core.annotation.Factory
import java.util.concurrent.TimeUnit

/**
 * @author jady
 * @since 2023/10/18 16/01
 * email: 1257984872@qq.com
 */
@Factory
class WillowConfig : BaseAndroidWillowConfig() {
    override fun HttpClientConfig<OkHttpConfig>.config() {
        engine {
            config {
                retryOnConnectionFailure(true) // 允许失败重试
                readTimeout(readTimeOut, TimeUnit.MILLISECONDS)
                connectTimeout(connectTimeOut, TimeUnit.MILLISECONDS)
                addInterceptor(HeaderInterceptor())
                addInterceptor(RetryShootInterceptor()) // 添加失败重试拦截器
                addInterceptor(RecordRequestTimeInterceptor())
                addInterceptor(SentryOkHttpInterceptor())
                addNetworkInterceptor(RequestSignInterceptor(true))
                cookieJar(MissEvanCookieJar.newInstance())
                protocols(arrayListOf(Protocol.HTTP_1_1, Protocol.HTTP_2))
                dns(MissEvanHttpDnsImpl())
                CronetBridge.inject(this)
            }
        }
    }
}

fun initWillow() {
    HttpDnsConfig.HOST = if (ApiConstants.isUat()) {
        arrayOf("app.uat.missevan.com", "fm.uat.missevan.com", "static-test.maoercdn.com")
    } else {
        arrayOf("app.missevan.com", "fm.missevan.com", "static.maoercdn.com")
    }
}
