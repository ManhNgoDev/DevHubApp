package com.manhngo.devhubapp.util.constant

import com.manhngo.devhubapp.BuildConfig

/**
 * Quản lý các biến môi trường và secret keys từ file .env / BuildConfig.
 * Hiện tại cấu hình gồm 2 thông tin xác thực GitHub OAuth.
 */
object EnvConfig {
    /**
     * GitHub OAuth Client ID
     */
    val GITHUB_CLIENT_ID: String
        get() = BuildConfig.GITHUB_CLIENT_ID

    /**
     * GitHub OAuth Client Secret
     */
    val GITHUB_CLIENT_SECRET: String
        get() = BuildConfig.GITHUB_CLIENT_SECRET
}
