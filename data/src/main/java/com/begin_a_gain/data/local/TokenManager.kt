package com.begin_a_gain.data.local

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val ACCESS_TOKEN = "access_token"
    private val REFRESH_TOKEN = "refresh_token"

    private val preferences = try {
        val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
        EncryptedSharedPreferences.create(
            "encrypted_prefs",
            masterKeyAlias,
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM,
        )
    } catch (e: Exception) {
        null
    }

    fun saveTokens(
        accessToken: String,
        refreshToken: String
    ) {
        saveAccessToken(accessToken = accessToken)
        saveRefreshToken(refreshToken = refreshToken)
    }

    private fun saveAccessToken(accessToken: String) {
        preferences?.edit()?.putString(ACCESS_TOKEN, accessToken)?.apply()
    }

    fun getAccessToken(): String {
        return preferences?.getString(ACCESS_TOKEN, "") ?: ""
    }

    private fun saveRefreshToken(refreshToken: String) {
        preferences?.edit()?.putString(REFRESH_TOKEN, refreshToken)?.apply()
    }

    fun getRefreshToken(): String {
        return preferences?.getString(REFRESH_TOKEN, "") ?: ""
    }

    fun clearTokens() {
        preferences?.edit()
            ?.remove(ACCESS_TOKEN)
            ?.remove(REFRESH_TOKEN)
            ?.apply()
    }

}