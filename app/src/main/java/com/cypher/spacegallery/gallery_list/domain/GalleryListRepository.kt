package com.cypher.spacegallery.gallery_list.domain

import com.cypher.spacegallery.gallery_list.domain.models.GalleryListItem

interface GalleryListRepository {
    suspend fun getImageList(): List<GalleryListItem>
}