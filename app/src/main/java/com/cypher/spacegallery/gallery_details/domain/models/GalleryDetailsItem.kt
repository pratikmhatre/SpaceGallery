package com.cypher.spacegallery.gallery_details.domain.models

data class GalleryDetailsItem(
    var copyright: String?,
    var date: String,
    val explanation: String,
    val hdUrl: String,
    val imageUrl: String,
    val title: String
)