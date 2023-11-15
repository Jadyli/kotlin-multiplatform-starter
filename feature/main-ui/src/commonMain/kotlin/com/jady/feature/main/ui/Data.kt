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
val httpClient = KoinPlatform.getKoin().get<HttpClient>()
val apiService = ApiService()

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

abstract class IApiService(baseUrl: String) {
    protected val urlBuilder = Url(baseUrl)

    /**
     * Executes a [HttpClient] GET request, with a URL built from [resource] and the information from the [builder]
     */
    protected suspend inline fun <reified T : Any> HttpClient.get(
        resource: T,
        builder: HttpRequestBuilder.() -> Unit = {}
    ): HttpResponse {
        return getBuilder {
            build<T>(resources(), resource, builder)
        }
    }

    /**
     * Executes a [HttpClient] POST request, with a URL built from [resource] and the information from the [builder]
     */
    protected suspend inline fun <reified T : Any> HttpClient.post(
        resource: T,
        builder: HttpRequestBuilder.() -> Unit = {}
    ): HttpResponse {
        return postBuilder {
            build<T>(resources(), resource, builder)
        }
    }

    /**
     * Executes a [HttpClient] PUT request, with a URL built from [resource] and the information from the [builder]
     */
    protected suspend inline fun <reified T : Any> HttpClient.put(
        resource: T,
        builder: HttpRequestBuilder.() -> Unit = {}
    ): HttpResponse {
        return putBuilder {
            build<T>(resources(), resource, builder)
        }
    }

    /**
     * Executes a [HttpClient] DELETE request, with a URL built from [resource] and the information from the [builder]
     */
    protected suspend inline fun <reified T : Any> HttpClient.delete(
        resource: T,
        builder: HttpRequestBuilder.() -> Unit = {}
    ): HttpResponse {
        return deleteBuilder {
            build<T>(resources(), resource, builder)
        }
    }

    protected inline fun <reified T : Any> HttpRequestBuilder.build(
        resources: io.ktor.resources.Resources,
        resource: T,
        builder: HttpRequestBuilder.() -> Unit
    ) {
        url {
            protocol = urlBuilder.protocol
            host = urlBuilder.host
            port = urlBuilder.port
            encodedPath = urlBuilder.encodedPath
        }
        href(resources.resourcesFormat, resource, url)
        builder()
    }

    @PublishedApi
    internal fun HttpClient.resources(): io.ktor.resources.Resources {
        return pluginOrNull(Resources) ?: throw IllegalStateException("Resources plugin is not installed")
    }
}

class ApiService : IApiService("https://www.random.org/") {
    suspend fun getRandomInteger(min: Int, max: Int): Int =
        httpClient.get(RandomNumber.RandomInteger(min = min, max = max)).body<String>().trim().toInt()
}
