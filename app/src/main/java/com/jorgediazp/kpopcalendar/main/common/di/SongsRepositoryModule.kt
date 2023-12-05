package com.jorgediazp.kpopcalendar.main.common.di

import com.jorgediazp.kpopcalendar.main.common.data.SongsRepository
import com.jorgediazp.kpopcalendar.main.common.data.SongsRemoteDataSource
import com.jorgediazp.kpopcalendar.main.common.domain.SongsDataSource
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