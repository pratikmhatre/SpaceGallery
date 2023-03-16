package com.cypher.spacegallery.gallery_details.domain

import com.cypher.spacegallery.core.helpers.Resource
import com.cypher.spacegallery.gallery_details.domain.models.GalleryDetailsItem

interface GalleryDetailsRepository {
    suspend fun getGalleryDetailsList(): Resource<List<GalleryDetailsItem>>
}