package com.example.amernotsapp.ui.preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

class CredentialsPreferences {
    companion object {
        private const val PREFERENCES_FILE_NAME = "credentials_preferences"

        const val TIMESTAMP_TOKEN_AUTH = "timestamp_token_auth"
        const val TOKEN_AUTH = "token_auth"

        fun getCredentialsPreferences(context: Context): SharedPreferences {
            val masterKey = MasterKey.Builder(context)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build()

            return EncryptedSharedPreferences.create(
                context,
                PREFERENCES_FILE_NAME,
                masterKey,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
        }
    }
}