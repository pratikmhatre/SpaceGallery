package com.cypher.spacegallery.gallery_list.di

import com.cypher.spacegallery.gallery_list.data.network.GalleryListRepositoryImpl
import com.cypher.spacegallery.gallery_list.domain.GalleryListRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object GalleryListModule {
    @Provides
    fun provideGalleryListRepository(repositoryImpl: GalleryListRepositoryImpl): GalleryListRepository =
        repositoryImpl
}