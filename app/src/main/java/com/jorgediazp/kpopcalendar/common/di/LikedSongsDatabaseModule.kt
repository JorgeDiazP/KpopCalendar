package com.jorgediazp.kpopcalendar.common.di

import android.content.Context
import androidx.room.Room
import com.jorgediazp.kpopcalendar.common.data.liked.local.LikedSongsDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LikedSongsDatabaseModule {

    @Provides
    @Singleton
    fun provideLikedSongsDatabase(@ApplicationContext context: Context): LikedSongsDataBase {
        return Room.databaseBuilder(
            context,
            LikedSongsDataBase::class.java,
            LikedSongsDataBase.DATABASE_NAME
        ).build()
    }
}