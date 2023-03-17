package com.cypher.spacegallery.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.cypher.spacegallery.core.Constants
import com.cypher.spacegallery.core.database.dao.GalleryItemsDao
import com.cypher.spacegallery.core.database.entities.GalleryItemTable

@Database(entities = [GalleryItemTable::class], version = Constants.DB_VERSION)
abstract class GalleryDb : RoomDatabase() {
    abstract fun galleryItemsDao(): GalleryItemsDao
}