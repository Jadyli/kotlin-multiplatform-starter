package com.jady.feature.main.ui

import io.ktor.client.HttpClient
import io.ktor.client.plugins.pluginOrNull
import io.ktor.client.plugins.resources.Resources
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.statement.HttpResponse
import io.ktor.http.Url
import io.ktor.http.encodedPath
import io.ktor.resources.href
import org.koin.mp.KoinPlatform
import io.ktor.client.request.delete as deleteBuilder
import io.ktor.client.request.get as getBuilder
import io.ktor.client.request.post as postBuilder
import io.ktor.client.request.put as putBuilder

/**
 * @author jady
 * @since 2023/11/14 19:53
 * email: 1257984872@qq.com
 */
val httpClient by lazy { KoinPlatform.getKoin().get<HttpClient>() }

open class BaseApiService(baseUrl: String) {
    @PublishedApi
    internal val urlBuilder = Url(baseUrl)

    /**
     * Executes a [HttpClient] GET request, with a URL built from [resource] and the information from the [builder]
     */
    suspend inline fun <reified T : Any> get(
        resource: T,
        builder: HttpRequestBuilder.() -> Unit = {}
    ): HttpResponse {
        return httpClient.getBuilder {
            build<T>(resource, builder)
        }
    }

    /**
     * Executes a [HttpClient] POST request, with a URL built from [resource] and the information from the [builder]
     */
    suspend inline fun <reified T : Any> post(
        resource: T,
        builder: HttpRequestBuilder.() -> Unit = {}
    ): HttpResponse {
        return httpClient.postBuilder {
            build<T>(resource, builder)
        }
    }

    /**
     * Executes a [HttpClient] PUT request, with a URL built from [resource] and the information from the [builder]
     */
    suspend inline fun <reified T : Any> put(
        resource: T,
        builder: HttpRequestBuilder.() -> Unit = {}
    ): HttpResponse {
        return httpClient.putBuilder {
            build<T>(resource, builder)
        }
    }

    /**
     * Executes a [HttpClient] DELETE request, with a URL built from [resource] and the information from the [builder]
     */
    suspend inline fun <reified T : Any> delete(
        resource: T,
        builder: HttpRequestBuilder.() -> Unit = {}
    ): HttpResponse {
        return httpClient.deleteBuilder {
            build<T>(resource, builder)
        }
    }

    @PublishedApi
    internal inline fun <reified T : Any> HttpRequestBuilder.build(
        resource: T,
        builder: HttpRequestBuilder.() -> Unit
    ) {
        url {
            protocol = urlBuilder.protocol
            host = urlBuilder.host
            port = urlBuilder.port
            encodedPath = urlBuilder.encodedPath
        }
        href(resources().resourcesFormat, resource, url)
        builder()
    }

    @PublishedApi
    internal fun resources(): io.ktor.resources.Resources {
        return httpClient.pluginOrNull(Resources) ?: throw IllegalStateException("Resources plugin is not installed")
    }
}
