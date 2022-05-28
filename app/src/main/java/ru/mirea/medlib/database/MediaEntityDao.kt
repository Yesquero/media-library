package ru.mirea.medlib.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface MediaEntityDao {

    @Transaction
    @Query("SELECT * FROM media_entity")
    fun getAllWithEpisode(): LiveData<List<MediaEntityWithEpisode>>
}