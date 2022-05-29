package ru.mirea.medlib.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MediaEntityDao {

    // CREATE
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(mediaEntity: MediaEntity): Long

    @Insert
    fun insert(episode: Episode)

    @Transaction
    fun insert(mediaEntity: MediaEntity, episodeList: List<Episode>) {
        val savedMediaId = insert(mediaEntity)

        episodeList.forEach {
            it.fkFilmId = savedMediaId
            insert(it)
        }
    }

    // READ

    @Transaction
    @Query("SELECT * FROM media_entity")
    fun getAllWithEpisode(): LiveData<List<MediaEntityWithEpisode>>

    // UPDATE

    // DELETE

    @Query("DELETE FROM media_entity WHERE kinopoiskId = :kinopoiskId")
    fun deleteMediaEntity(kinopoiskId: Long)

}