package com.jady.feature.startup

import android.content.Context
import androidx.annotation.Keep
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

/**
 * @author jady
 * @since 2023/11/13 11:19
 * email: 1257984872@qq.com
 */
@Keep
actual class StartUp(private val mContext: Context) : CoroutineScope by MainScope() {
    actual fun initLibs() {
    }
}
