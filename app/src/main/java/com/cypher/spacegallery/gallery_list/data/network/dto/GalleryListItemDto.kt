package com.cypher.spacegallery.gallery_list.data.network.dto

import com.google.gson.annotations.SerializedName
import com.cypher.spacegallery.database.entities.GalleryItemTable
import com.cypher.spacegallery.gallery_list.domain.models.GalleryListItem


data class GalleryListItemDto(
    val date: String,
    val explanation: String,

    @SerializedName("hdurl")
    val hdUrl: String,

    val copyright: String?,

    @SerializedName("media_type")
    val mediaType: String,

    @SerializedName("service_version")
    val serviceVersion: String,

    val title: String,
    val url: String
) {
    fun toGalleryItemTable() = GalleryItemTable(
        date = date,
        explanation = explanation,
        hdUrl = hdUrl,
        mediaType = mediaType,
        serviceVersion = serviceVersion,
        copyright = copyright,
        title = title,
        url = url
    )
}
