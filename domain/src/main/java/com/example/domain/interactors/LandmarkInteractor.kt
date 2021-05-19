package com.example.domain.interactors

import com.example.domain.interfaces.LandDBRepository
import com.example.domain.interfaces.LandmarkRepository
import com.example.domain.models.Landmark
import javax.inject.Inject

class LandmarkInteractor @Inject constructor(
    private val landmarkRepository: LandmarkRepository,
    private val landmarkDBRepository: LandDBRepository
) {
    suspend fun getLandmark():List<Landmark> {
        val list = landmarkRepository.getLandmarks()
        list.forEach { it.isFavorite = landmarkDBRepository.isFavorite(it.id) }
        return list
    }

    suspend fun likeLand(id: Int) = landmarkDBRepository.likeLand(id)
    suspend fun dislikeLand(id: Int) = landmarkDBRepository.dislikeLand(id)
    suspend fun isFav(id: Int): Boolean = landmarkDBRepository.isFavorite(id)
    suspend fun getLandById(id: Int): Landmark? {
        val list = landmarkRepository.getLandmarks()
        list.forEach { if (it.id == id) return it }
        return null
    }
}