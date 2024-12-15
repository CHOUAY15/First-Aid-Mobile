package com.example.firstaidfront.api


import com.example.firstaidfront.models.Participant
import com.example.firstaidfront.models.ParticipantAnswer
import com.example.firstaidfront.models.TestResult
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ParticipantService {
    @GET("PARTICIPANT-SERVICE/participants/get/{userId}")
    suspend fun getParticipant(@Path("userId") userId: String): Participant
    @POST("PARTICIPANT-SERVICE/participants/{participantId}/trainings/{trainingId}/submit-test")
    suspend fun submitTest(
        @Path("participantId") participantId: Int,
        @Path("trainingId") trainingId: Int,
        @Body answers: List<ParticipantAnswer>
    ): TestResult
}