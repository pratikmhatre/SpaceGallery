package com.cypher.spacegallery.gallery_list.domain.usecases

import com.cypher.spacegallery.core.helpers.Resource
import com.cypher.spacegallery.gallery_list.domain.GalleryListRepository
import com.cypher.spacegallery.gallery_list.domain.models.GalleryListItem
import javax.inject.Inject

class GetGalleryList @Inject constructor(private val repository: GalleryListRepository) {
    suspend operator fun invoke(): Resource<List<GalleryListItem>> {
        return repository.getImageList()
    }
}