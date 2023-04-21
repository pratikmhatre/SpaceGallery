package com.cypher.spacegallery.gallery_list.data.network

import com.cypher.spacegallery.core.database.entities.GalleryItemTable
import com.cypher.spacegallery.core.helpers.Resource
import com.cypher.spacegallery.gallery_list.domain.GalleryListRepository
import com.cypher.spacegallery.gallery_list.domain.models.GalleryListItem

class DummyGalleryListRepository : GalleryListRepository {
    private var simulateError = false
    override suspend fun getImageList(): List<GalleryListItem> {
        return if (simulateError) {
            listOf()
        } else {
            val dummyList = getDummyItemList(5).map {
                GalleryListItem(it.title, it.url)
            }
            dummyList
        }
    }

    fun shouldSimulateError(showError: Boolean) {
        simulateError = showError
    }

    private fun getDummyItemList(count: Int): List<GalleryItemTable> {
        val list = List(count) {
            GalleryItemTable(
                id = it.toLong(),
                date = "Date $it",
                explanation = "Explanation $it",
                hdUrl = "Hd Url $it",
                copyright = null,
                mediaType = "Type $it",
                serviceVersion = "Version $it",
                title = "Title $it",
                url = "Url $it",
            )
        }
        return list
    }
}