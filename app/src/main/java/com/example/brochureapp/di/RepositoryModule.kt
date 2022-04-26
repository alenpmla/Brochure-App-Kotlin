package com.example.brochureapp.di

import com.example.brochureapp.data.repository.BrochureRepositoryImpl
import com.example.brochureapp.domain.repository.BrochureRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun buildBrochureRepository(
        brochureRepositoryImpl: BrochureRepositoryImpl
    ): BrochureRepository
}
