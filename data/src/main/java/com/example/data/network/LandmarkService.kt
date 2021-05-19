package com.example.data.network

import com.example.domain.models.Landmark
import retrofit2.http.GET

interface LandmarkService {
    @GET("candidates--questionnaire.appspot.com/o/landmarkData.json")
    suspend fun getLandmarks(): List<Landmark>
}