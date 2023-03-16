package com.cypher.spacegallery.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.cypher.spacegallery.core.Constants
import com.cypher.spacegallery.database.dao.GalleryItemsDao
import com.cypher.spacegallery.database.entities.GalleryItemTable

@Database(entities = [GalleryItemTable::class], version = Constants.DB_VERSION)
abstract class GalleryDb : RoomDatabase() {
    abstract fun galleryItemsDao(): GalleryItemsDao
}