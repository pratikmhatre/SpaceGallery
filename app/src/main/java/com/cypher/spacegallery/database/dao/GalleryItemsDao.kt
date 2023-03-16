package com.cypher.spacegallery.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.cypher.spacegallery.core.Constants
import com.cypher.spacegallery.database.entities.GalleryItemTable

@Dao
interface GalleryItemsDao {
    @Insert
    suspend fun insertGalleryItemsList(items: List<GalleryItemTable>)

    @Query("SELECT * FROM ${Constants.GALLERY_TABLE}")
    suspend fun getGalleryItemsList(): List<GalleryItemTable>

    @Update
    suspend fun updateGalleryItem(item: GalleryItemTable)
}