package com.example.amernotsapp.di

import com.example.amernotsapp.data.api.repository.AmernotsApiReposotoryImpl
import com.example.amernotsapp.domain.repository.AmernotsApiRepository
import dagger.Binds
import dagger.Module

@Module
abstract class AppBindsModule {
    @Binds
    abstract fun bindAmernotsApiRepositoryImpl_to_AmernotsApiRepository(
        repositoryImpl: AmernotsApiReposotoryImpl
    ): AmernotsApiRepository
}