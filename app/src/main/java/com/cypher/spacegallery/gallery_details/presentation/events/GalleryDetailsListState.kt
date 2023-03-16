package com.cypher.spacegallery.gallery_details.presentation.events

import com.cypher.spacegallery.gallery_details.domain.models.GalleryDetailsItem

data class GalleryDetailsListState(
    val isLoading: Boolean = false,
    val galleryDetailsList: List<GalleryDetailsItem>? = null
)