package com.example.wallpaperapp.di

import com.example.wallpaperapp.data.repository.WallPaperRepositoryImpl
import com.example.wallpaperapp.domain.repository.WallPaperRepository
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
    abstract fun bindWallPaperRepository(
        impl: WallPaperRepositoryImpl
    ): WallPaperRepository
}
