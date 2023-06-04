package com.example.amernotsapp.di

import com.example.amernotsapp.data.api.repository.AmernotsApiRepositoryImpl
import com.example.amernotsapp.data.api.repository.DaDataRepositoryImpl
import com.example.amernotsapp.domain.repository.AmernotsApiRepository
import com.example.amernotsapp.domain.repository.DaDataRepository
import dagger.Binds
import dagger.Module

@Module
abstract class AppBindsModule {
    @Binds
    abstract fun bindAmernotsApiRepositoryImpl_to_AmernotsApiRepository(
        repositoryImpl: AmernotsApiRepositoryImpl
    ): AmernotsApiRepository

    @Binds
    abstract fun bindDaDataRepositoryImpl_to_DaDataRepository(
        repositoryImpl: DaDataRepositoryImpl
    ): DaDataRepository
}