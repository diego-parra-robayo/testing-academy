package com.wizeline.academy.testing.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
abstract class FavoritesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(favorite: FavoriteEntity)

    suspend fun addFavorite(movieId: String) =
        insert(favorite = FavoriteEntity(movieId, System.currentTimeMillis()))

    @Query("DELETE FROM Favorites WHERE movieId = :movieId")
    abstract suspend fun removeFavorite(movieId: String)

    @Query("SELECT movieId FROM Favorites")
    abstract suspend fun getFavorites(): List<String>

    @Query("SELECT movieId FROM Favorites")
    abstract fun getFavoritesAsFlow(): Flow<List<String>>

    @Query("SELECT EXISTS(SELECT * FROM Favorites WHERE movieId = :movieId)")
    abstract fun isFavourite(movieId: String): Boolean

    @Query("SELECT EXISTS(SELECT * FROM Favorites WHERE movieId = :movieId)")
    abstract fun isFavouriteAsFlow(movieId: String): Flow<Boolean>

}