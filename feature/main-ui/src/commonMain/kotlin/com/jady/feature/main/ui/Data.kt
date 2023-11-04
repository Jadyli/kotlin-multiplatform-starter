package com.jady.feature.main.ui

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.resources.*
import io.ktor.client.request.*
import io.ktor.resources.*
import kotlinx.serialization.Serializable
import org.koin.mp.KoinPlatform

/**
 * @author jady
 * @since 2023/11/4 17:58
 * email: 1257984872@qq.com
 */
val httpClient = KoinPlatform.getKoin().get<HttpClient>()
val apiService = ApiService()

@Resource("https://www.random.org/")
class RandomNumber {
    @Resource("integers")
    class RandomInteger(val parent: RandomNumber = RandomNumber()) {
        @Serializable
        data class RandomIntegerParam(val num: Int, val col: Int, val base: Int, val format: String, val min: Int, val max: Int)
    }
}

class ApiService {
    suspend fun getRandomInteger(min: Int, max: Int): Int = httpClient.get(RandomNumber.RandomInteger()) {
        setBody(RandomNumber.RandomInteger.RandomIntegerParam(num = 1, col = 1, base = 10, format = "plain", min = min, max = max))
    }.body()
}



