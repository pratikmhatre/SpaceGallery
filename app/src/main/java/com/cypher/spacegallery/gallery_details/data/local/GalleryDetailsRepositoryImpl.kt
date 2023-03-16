package com.cypher.spacegallery.gallery_details.data.local

import androidx.annotation.WorkerThread
import com.cypher.spacegallery.core.helpers.Resource
import com.cypher.spacegallery.database.dao.GalleryItemsDao
import com.cypher.spacegallery.gallery_details.domain.GalleryDetailsRepository
import com.cypher.spacegallery.gallery_details.domain.models.GalleryDetailsItem
import javax.inject.Inject

class GalleryDetailsRepositoryImpl @Inject constructor(private val galleryItemsDao: GalleryItemsDao) :
    GalleryDetailsRepository {

    @WorkerThread
    override suspend fun getGalleryDetailsList(): Resource<List<GalleryDetailsItem>> {
        return try {
            val imageData = galleryItemsDao.getGalleryItemsList().map {
                GalleryDetailsItem(
                    copyright = it.copyright,
                    date = it.date,
                    explanation = it.explanation,
                    hdUrl = it.hdUrl,
                    imageUrl = it.url,
                    title = it.title
                )
            }
            Resource.Success(imageData)
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }
}