package com.cypher.spacegallery.gallery_list.domain.usecases

import com.cypher.spacegallery.core.helpers.Resource
import com.cypher.spacegallery.gallery_list.domain.GalleryListRepository
import com.cypher.spacegallery.gallery_list.domain.models.GalleryListItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetGalleryList @Inject constructor(private val repository: GalleryListRepository) {
    operator fun invoke(): Flow<Resource<List<GalleryListItem>>> = flow {
        emit(Resource.Loading())

        //simulate network delay
        delay(500)

        val result = repository.getImageList()
        if (result.isNotEmpty()) {
            emit(Resource.Success(result))
        } else {
            emit(Resource.Error(Exception()))
        }
    }
}