package com.manhngo.devhubapp.data.local

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

/**
 * Manages encrypted local key-value storage using AndroidX Security Crypto (EncryptedSharedPreferences).
 * All data stored here is encrypted using AES-256 backed by the Android Keystore.
 * Suitable for Auth Tokens, Session IDs, sensitive user data, etc.
 */
class SecureStorageManager(context: Context) {

    private val masterKey: MasterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val sharedPreferences: SharedPreferences = EncryptedSharedPreferences.create(
        context,
        SECURE_PREFS_NAME,
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    fun saveString(key: String, value: String?) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    fun getString(key: String, defaultValue: String? = null): String? {
        return sharedPreferences.getString(key, defaultValue)
    }

    fun saveBoolean(key: String, value: Boolean) {
        sharedPreferences.edit().putBoolean(key, value).apply()
    }

    fun getBoolean(key: String, defaultValue: Boolean = false): Boolean {
        return sharedPreferences.getBoolean(key, defaultValue)
    }

    fun remove(key: String) {
        sharedPreferences.edit().remove(key).apply()
    }

    fun clearAll() {
        sharedPreferences.edit().clear().apply()
    }

    // Convenient helpers for common authentication secrets
    fun saveAuthToken(token: String) = saveString(KEY_AUTH_TOKEN, token)
    fun getAuthToken(): String? = getString(KEY_AUTH_TOKEN)
    fun clearAuthToken() = remove(KEY_AUTH_TOKEN)

    companion object {
        private const val SECURE_PREFS_NAME = "devhub_secure_prefs"
        private const val KEY_AUTH_TOKEN = "key_auth_token"

        @Volatile
        private var INSTANCE: SecureStorageManager? = null

        fun getInstance(context: Context): SecureStorageManager {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: SecureStorageManager(context.applicationContext).also { INSTANCE = it }
            }
        }
    }
}
