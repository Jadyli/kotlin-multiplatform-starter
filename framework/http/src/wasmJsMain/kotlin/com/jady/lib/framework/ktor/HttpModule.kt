/*
 * Copyright (c) 2015-2023 BiliBili Inc.
 */

package com.jady.lib.framework.http

import io.ktor.client.*

/**
 * @author jady
 * @since 2023/09/08 17/02
 * email: 1257984872@qq.com
 */
actual val httpClient: HttpClient = HttpClient(CIO) {
}
