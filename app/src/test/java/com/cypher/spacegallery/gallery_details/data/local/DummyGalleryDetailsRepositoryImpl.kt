package com.cypher.spacegallery.gallery_details.data.local

import com.cypher.spacegallery.core.helpers.Resource
import com.cypher.spacegallery.core.helpers.Utils
import com.cypher.spacegallery.gallery_details.domain.GalleryDetailsRepository
import com.cypher.spacegallery.gallery_details.domain.models.GalleryDetailsItem
import org.junit.Assert.*

import org.junit.Test

class DummyGalleryDetailsRepositoryImpl : GalleryDetailsRepository {
    override suspend fun getGalleryDetailsList(): Resource<List<GalleryDetailsItem>> {
        val dummyList = Utils.getDummyItemList(20)
        val imageData = dummyList.map {
            GalleryDetailsItem(
                copyright = it.copyright,
                date = it.date,
                explanation = it.explanation,
                hdUrl = it.hdUrl,
                imageUrl = it.url,
                title = it.title
            )
        }
        return Resource.Success(imageData)
    }
}