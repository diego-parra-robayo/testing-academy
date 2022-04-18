package com.wizeline.academy.testing.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Favorites")
data class FavoriteEntity(
    @PrimaryKey val movieId: String,
    val updatedAtMillis: Long
)