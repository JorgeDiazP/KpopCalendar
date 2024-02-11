package com.jorgediazp.kpopcalendar.common.di

import com.jorgediazp.kpopcalendar.common.data.songs.SongsRepository
import com.jorgediazp.kpopcalendar.common.data.songs.remote.SongsRemoteDataSource
import com.jorgediazp.kpopcalendar.common.domain.usecase.SongsDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SongsRepositoryModule {

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class SongsRepositoryQualifier

    @Binds
    @Singleton
    @SongsRepositoryQualifier
    abstract fun bindSongsRepository(repository: SongsRepository): SongsDataSource

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class SongsDataSourceQualifier

    @Binds
    @Singleton
    @SongsDataSourceQualifier
    abstract fun bindSongsRemoteDataSource(remoteDataSource: SongsRemoteDataSource): SongsDataSource
}