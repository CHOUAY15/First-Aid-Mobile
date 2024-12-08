package com.example.firstaidfront.models

import com.google.gson.annotations.SerializedName

data class AuthResponse(
    @SerializedName("access_token")
    val accessToken: String,
    @SerializedName("refresh_token")
    val refreshToken: String,
    @SerializedName("participant_id")
    val participantId: Int,
    @SerializedName("user_id")
    val userId: String
)