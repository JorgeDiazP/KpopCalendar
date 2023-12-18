package com.jorgediazp.kpopcalendar.home.common.di

import com.jorgediazp.kpopcalendar.home.common.data.liked.LikedSongsRepository
import com.jorgediazp.kpopcalendar.home.common.data.liked.local.LikedSongsLocalDataSource
import com.jorgediazp.kpopcalendar.home.common.domain.usecase.LikedSongsDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class LikedSongsRepositoryModule {

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class LikedSongsRepositoryQualifier

    @Binds
    @Singleton
    @LikedSongsRepositoryQualifier
    abstract fun bindLikedSongsRepository(repository: LikedSongsRepository): LikedSongsDataSource

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class LikedSongsDataSourceQualifier

    @Binds
    @Singleton
    @LikedSongsDataSourceQualifier
    abstract fun bindLikedSongsLocalDataSource(localDataSource: LikedSongsLocalDataSource): LikedSongsDataSource
}