package com.jady.kotlin.multiplatform

import android.app.Application
import com.jady.feature.startup.StartUp
import com.jady.feature.startup.getCommonModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

/**
 * @author jady
 * @since 2023/11/13 11:30
 * email: 1257984872@qq.com
 */
class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        StartUp(applicationContext).initLibs()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(applicationContext)
            modules(getCommonModules())
        }
    }
}
