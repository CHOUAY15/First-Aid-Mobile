package com.example.firstaidfront.api

import com.example.firstaidfront.models.Category
import retrofit2.http.GET

interface TrainingService {
    @GET("TRAINING-SERVICE/trainings")
    suspend fun getTrainings(): List<Category>
}