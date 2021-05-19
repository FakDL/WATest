package com.example.domain.interfaces


interface LandDBRepository {
    suspend fun likeLand(id: Int)
    suspend fun dislikeLand(id: Int)
    suspend fun isFavorite(id: Int): Boolean
}