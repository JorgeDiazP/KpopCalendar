package com.jorgediazp.kpopcalendar.main.common.di

import com.jorgediazp.kpopcalendar.main.common.data.ComebacksRepository
import com.jorgediazp.kpopcalendar.main.common.data.ComebacksRemoteDataSource
import com.jorgediazp.kpopcalendar.main.common.domain.ComebacksDataSource
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