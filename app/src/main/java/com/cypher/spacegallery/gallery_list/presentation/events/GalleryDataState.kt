package com.cypher.spacegallery.gallery_list.presentation.events

import com.cypher.spacegallery.gallery_list.domain.models.GalleryListItem

data class GalleryDataState(
    val isLoading: Boolean = false,
    val galleryData: List<GalleryListItem>? = null
)