package com.jady.kotlin.multiplatform

import android.app.Application
import com.jady.feature.startup.StartUp

/**
 * @author jady
 * @since 2023/11/13 11:30
 * email: 1257984872@qq.com
 */
class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        StartUp(applicationContext).initLibs()
    }
}
