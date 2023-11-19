package com.jorgediazp.kpopcomebacks.main.di

import com.jorgediazp.kpopcomebacks.main.data.ComebacksRepository
import com.jorgediazp.kpopcomebacks.main.data.remote.ComebacksRemoteDataSource
import com.jorgediazp.kpopcomebacks.main.domain.ComebacksDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ComebacksRepositoryModule {

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class ComebacksRepositoryQualifier

    @Binds
    @Singleton
    @ComebacksRepositoryQualifier
    abstract fun bindComebacksRepository(repository: ComebacksRepository): ComebacksDataSource

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class ComebacksDataSourceQualifier

    @Binds
    @Singleton
    @ComebacksDataSourceQualifier
    abstract fun bindComebacksRemoteDataSource(remoteDataSource: ComebacksRemoteDataSource): ComebacksDataSource
}