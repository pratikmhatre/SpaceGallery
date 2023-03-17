package com.cypher.spacegallery.core.di

import android.content.Context
import androidx.room.Room
import com.cypher.spacegallery.core.Constants
import com.cypher.spacegallery.core.database.GalleryDb
import com.cypher.spacegallery.core.database.dao.GalleryItemsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideGalleryDb(@ApplicationContext context: Context): GalleryDb {
        return Room.databaseBuilder(context, GalleryDb::class.java, Constants.DB_NAME).build()
    }

    @Singleton
    @Provides
    fun provideGalleryDao(db: GalleryDb): GalleryItemsDao {
        return db.galleryItemsDao()
    }
}