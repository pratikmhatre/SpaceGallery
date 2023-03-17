package com.cypher.spacegallery.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.cypher.spacegallery.core.database.GalleryDb
import com.cypher.spacegallery.core.database.dao.GalleryItemsDao
import com.cypher.spacegallery.core.database.entities.GalleryItemTable
import com.cypher.spacegallery.core.helpers.Utils
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@SmallTest
@RunWith(AndroidJUnit4::class)
class GalleryDbTest {
    @get:Rule
    val instantRule = InstantTaskExecutorRule()
    private lateinit var galleryDb: GalleryDb
    private lateinit var galleryItemsDao: GalleryItemsDao

    @Before
    fun setUp() {
        galleryDb = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            GalleryDb::class.java
        ).allowMainThreadQueries().build()
        galleryItemsDao = galleryDb.galleryItemsDao()
    }

    @Test
    fun insertGalleryItemsListTest() = runBlocking {
        val size = 100
        val dummyList = Utils.getDummyItemList(size)
        //save
        galleryItemsDao.insertGalleryItemsList(dummyList)

        //fetch
        val storedList = galleryItemsDao.getGalleryItemsList()

        assertThat(storedList).hasSize(size)
    }

    @Test
    fun deleteAllGalleryItemsTest() = runBlocking {
        val size = 50
        val dummyList = Utils.getDummyItemList(size)

        //save
        galleryItemsDao.insertGalleryItemsList(dummyList)

        //delete
        galleryItemsDao.deleteAllGalleryItems()

        //fetch
        val storedList = galleryItemsDao.getGalleryItemsList()

        assertThat(storedList).isEmpty()
    }


    @After
    fun tearDown() {
        galleryDb.close()
    }

}