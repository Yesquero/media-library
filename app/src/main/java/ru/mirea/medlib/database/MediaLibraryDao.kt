package ru.mirea.medlib.database

import androidx.lifecycle.LiveData
import androidx.room.*
import ru.mirea.medlib.domain.MediaDetails

@Dao
interface MediaLibraryDao {

    // CREATE
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(mediaEntity: MediaEntity): Long

    @Insert
    fun insert(episode: Episode)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(staffEntity: StaffEntity): Long

    @Insert
    fun insert(crossRef: MediaStaffCrossRef)

    @Transaction
    fun insertWithEpisode(
        mediaEntity: MediaEntity,
        staffList: List<StaffEntity>,
        episodeList: List<Episode>
    ) {
        val savedMediaId = insert(mediaEntity)

        saveStaff(savedMediaId, staffList)

        episodeList.forEach {
            it.fkFilmId = savedMediaId
            insert(it)
        }
    }

    @Transaction
    fun insert(mediaEntity: MediaEntity, staffList: List<StaffEntity>) {
        val savedMediaId = insert(mediaEntity)

        saveStaff(savedMediaId, staffList)
    }

    // READ

    @Transaction
    @Query("SELECT * FROM media_entity")
    fun getAllWithEpisode(): LiveData<List<MediaEntityFull>>

    // UPDATE

    // DELETE

    @Query("DELETE FROM media_staff_crossRef WHERE kinopoiskId = :kinopoiskId")
    fun deleteMediaCrossRef(kinopoiskId: Long)

    @Transaction
    fun deleteMediaFull(mediaDetails: MediaDetails) {
        // delete all associations via crossRef table
        deleteMediaCrossRef(mediaDetails.kinopoiskId)

        // delete all associated staff
        mediaDetails.staffList.forEach { deleteStaffById(it.internalId) }

        // delete entity, also cascade deletes episodes
        deleteMediaEntity(mediaDetails.kinopoiskId)
    }

    @Query("DELETE FROM media_entity WHERE kinopoiskId = :kinopoiskId")
    fun deleteMediaEntity(kinopoiskId: Long)

    @Query("DELETE FROM staff WHERE internalId = :internalId")
    fun deleteStaffById(internalId: Long)

    @Query("DELETE FROM episode WHERE film_id = :kinopoiskId")
    fun deleteAssociatedEpisodes(kinopoiskId: Long)

    /*
    UTILITY FUNCTIONS
     */

    @Query("SELECT EXISTS(SELECT * FROM media_entity WHERE kinopoiskId = :kinopoiskId)")
    fun mediaExists(kinopoiskId: Long): Boolean

    private fun saveStaff(mediaId: Long, staffList: List<StaffEntity>) {
        staffList.forEach {
            val savedStaffId = insert(it)
            insert(MediaStaffCrossRef(mediaId, savedStaffId))
        }
    }
}