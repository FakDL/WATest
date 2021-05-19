package com.example.domain.models

data class Landmark(
    val category: String,
    val city: String,
    val coordinates: Coordinates,
    val id: Int,
    val imageName: String,
    var isFavorite: Boolean,
    val name: String,
    val park: String,
    val state: String
)