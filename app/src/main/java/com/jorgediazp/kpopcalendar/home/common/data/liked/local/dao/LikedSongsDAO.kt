package com.jorgediazp.kpopcalendar.home.common.data.liked.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jorgediazp.kpopcalendar.home.common.data.liked.local.model.LikedSongDataModel
import kotlinx.coroutines.flow.Flow

@Dao
interface LikedSongsDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLikedSong(song: LikedSongDataModel)

    @Query("SELECT * FROM LikedSongs")
    fun getAllLikedSongs(): Flow<List<LikedSongDataModel>>

    @Query("DELETE FROM LikedSongs WHERE id = :songId")
    suspend fun deleteLikedSong(songId: Long)
}