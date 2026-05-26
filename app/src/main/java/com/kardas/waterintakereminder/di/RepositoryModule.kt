package com.kardas.waterintakereminder.di

import com.kardas.waterintakereminder.data.repository.WaterRepositoryImpl
import com.kardas.waterintakereminder.domain.repository.WaterRepository
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
    abstract fun bindWaterRepository(
        impl: WaterRepositoryImpl
    ): WaterRepository
}

