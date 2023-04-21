package com.cypher.spacegallery.gallery_list.data.network

import android.content.Context
import androidx.annotation.WorkerThread
import com.cypher.spacegallery.core.Constants
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.cypher.spacegallery.core.helpers.Resource
import com.cypher.spacegallery.core.database.dao.GalleryItemsDao
import com.cypher.spacegallery.gallery_list.data.network.dto.GalleryListItemDto
import com.cypher.spacegallery.gallery_list.domain.GalleryListRepository
import com.cypher.spacegallery.gallery_list.domain.models.GalleryListItem
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class GalleryListRepositoryImpl @Inject constructor(
    @ApplicationContext val context: Context,
    private val galleryItemsDao: GalleryItemsDao
) :
    GalleryListRepository {

    @WorkerThread
    override suspend fun getImageList(): List<GalleryListItem> {
        val rawData = getStringDataFromFile(context, Constants.JSON_FILE_NAME)

        val dataToke = object : TypeToken<List<GalleryListItemDto>>() {}.type
        val galleryData: List<GalleryListItemDto> = Gson().fromJson(rawData, dataToke)

        val galleryTableList = galleryData.map {
            it.toGalleryItemTable()
        }

        //delete existing records
        galleryItemsDao.deleteAllGalleryItems()

        //save to db
        galleryItemsDao.insertGalleryItemsList(galleryTableList)

        //fetch from db
        val savedList = galleryItemsDao.getGalleryItemsList().map {
            GalleryListItem(it.title, it.url)
        }
        return savedList
    }

    private fun getStringDataFromFile(context: Context, fileName: String): String {
        return context.assets.open(fileName).bufferedReader().use {
            it.readText()
        }
    }
}