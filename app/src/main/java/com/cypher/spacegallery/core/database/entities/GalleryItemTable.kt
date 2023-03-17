package com.cypher.spacegallery.core.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.cypher.spacegallery.core.Constants

@Entity(tableName = Constants.GALLERY_TABLE)
data class GalleryItemTable(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,

    val date: String,
    val explanation: String,
    val hdUrl: String,
    val copyright: String?,
    val mediaType: String,
    val serviceVersion: String,
    val title: String,
    val url: String
)


