package com.example.firstaidfront.config

import android.content.Context
import android.util.Log
import androidx.core.content.edit
import com.example.firstaidfront.models.AuthResponse

object TokenManager {
    private const val PREF_NAME = "auth_prefs"
    private const val KEY_ACCESS_TOKEN = "access_token"
    private const val KEY_REFRESH_TOKEN = "refresh_token"
    private const val KEY_PARTICIPANT_ID = "participant_id"
    private const val KEY_USER_ID = "user_id"

    fun saveAuthData(context: Context, authResponse: AuthResponse) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit {
            putString(KEY_ACCESS_TOKEN, authResponse.accessToken)
            putString(KEY_REFRESH_TOKEN, authResponse.refreshToken)
            putInt(KEY_PARTICIPANT_ID, authResponse.participantId)
            putString(KEY_USER_ID, authResponse.userId)
        }
        Log.d("AuthData", "Saved Auth Data: AccessToken=${authResponse.accessToken}, RefreshToken=${authResponse.refreshToken}, ParticipantId=${authResponse.participantId}, UserId=${authResponse.userId}")
    }

    fun getAccessToken(context: Context): String? {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            .getString(KEY_ACCESS_TOKEN, null)
    }

    fun getParticipantId(context: Context): Int? {
        val id = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            .getInt(KEY_PARTICIPANT_ID, -1)
        return if (id != -1) id else null
    }

    fun clearAuthData(context: Context) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit().clear().apply()
    }
}