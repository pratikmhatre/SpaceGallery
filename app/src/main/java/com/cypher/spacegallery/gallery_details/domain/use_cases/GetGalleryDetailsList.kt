package com.cypher.spacegallery.gallery_details.domain.use_cases

import com.cypher.spacegallery.core.helpers.Resource
import com.cypher.spacegallery.core.helpers.Utils
import com.cypher.spacegallery.gallery_details.domain.GalleryDetailsRepository
import com.cypher.spacegallery.gallery_details.domain.models.GalleryDetailsItem
import javax.inject.Inject

class GetGalleryDetailsList @Inject constructor(private val repository: GalleryDetailsRepository) {
    suspend operator fun invoke(): Resource<List<GalleryDetailsItem>> {
        val result = repository.getGalleryDetailsList()
        if (result is Resource.Success) {
            result.data?.map {
                it.date = Utils.getFormattedDate(it.date)
                it.copyright?.run { it.copyright = Utils.prependCopyrightSymbol(this) }
            }
        }
        return result
    }
}