/*
 * Copyright (c) 2015-2023 BiliBili Inc.
 */

package com.jady.feature.startup.config

import cn.missevan.lib.utils.OnlineConfig
import com.bilibili.lib.blconfig.ConfigManager
import org.koin.core.annotation.Singleton

/**
 * @author jady
 * @since 2023/10/27 12:30
 * email: 1257984872@qq.com
 */
@Singleton
class OnlineConfigAndroid : OnlineConfig {
    override fun ab(key: String) = ConfigManager.ab()[key]

    override fun config(key: String, defValue: String?) = ConfigManager.config()[key, defValue]
}
