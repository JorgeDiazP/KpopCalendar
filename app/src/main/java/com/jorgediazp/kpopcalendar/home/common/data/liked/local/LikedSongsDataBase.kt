package com.jorgediazp.kpopcalendar.home.common.data.liked.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jorgediazp.kpopcalendar.home.common.data.liked.local.dao.LikedSongsDAO
import com.jorgediazp.kpopcalendar.home.common.data.liked.local.model.LikedSongDataModel

@Database(
    entities = [LikedSongDataModel::class],
    version = 1,
    exportSchema = false
)
abstract class LikedSongsDataBase : RoomDatabase() {

    companion object {
        const val DATABASE_NAME = "liked_songs.db"
    }

    abstract fun songsDAO(): LikedSongsDAO
}