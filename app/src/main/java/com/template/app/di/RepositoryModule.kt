package com.template.app.di

import com.template.app.data.repository.PlaceholderRepositoryImpl
import com.template.app.domain.repository.PlaceholderRepository
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
    abstract fun bindPlaceholderRepository(
        impl: PlaceholderRepositoryImpl
    ): PlaceholderRepository
}
