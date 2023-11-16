package com.jady.feature.main.ui

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.resources.Resources
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.resources.*
import kotlinx.serialization.Serializable
import org.koin.mp.KoinPlatform
import io.ktor.client.request.delete as deleteBuilder
import io.ktor.client.request.get as getBuilder
import io.ktor.client.request.post as postBuilder
import io.ktor.client.request.put as putBuilder

/**
 * @author jady
 * @since 2023/11/4 17:58
 * email: 1257984872@qq.com
 */
val apiService by lazy { ApiService() }

@Serializable
class RandomNumber {
    @Resource("integers/")
    class RandomInteger(
        val parent: RandomNumber = RandomNumber(),
        val num: Int = 1,
        val col: Int = 1,
        val base: Int = 10,
        val format: String = "plain",
        val min: Int,
        val max: Int
    )
}

class ApiService : BaseApiService("https://www.random.org/")
