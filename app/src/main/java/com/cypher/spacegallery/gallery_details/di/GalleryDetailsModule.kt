package com.cypher.spacegallery.gallery_details.di

import com.cypher.spacegallery.gallery_details.data.local.GalleryDetailsRepositoryImpl
import com.cypher.spacegallery.gallery_details.domain.GalleryDetailsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object GalleryDetailsModule {
    @Provides
    fun provideGalleryDetailsRepository(repository: GalleryDetailsRepositoryImpl): GalleryDetailsRepository {
        return repository
    }
}