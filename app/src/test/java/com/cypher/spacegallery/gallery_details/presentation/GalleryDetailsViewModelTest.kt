package com.cypher.spacegallery.gallery_details.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.cypher.spacegallery.gallery_details.data.local.DummyGalleryDetailsRepositoryImpl
import com.cypher.spacegallery.gallery_details.domain.use_cases.GetGalleryDetailsList
import com.cypher.utils.MainDispatcherRule
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GalleryDetailsViewModelTest {
    private lateinit var viewModel: GalleryDetailsViewModel
    @get:Rule
    val taskRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = MainDispatcherRule()

    @Before
    fun setup() {
        val dummyRepository = DummyGalleryDetailsRepositoryImpl()
        val useCase = GetGalleryDetailsList(dummyRepository)
        viewModel = GalleryDetailsViewModel(useCase)
    }

    @Test
    fun `fetch gallery details emits stored list data with Success response`() = runTest {
        viewModel.fetchGalleryDetailsList()
        viewModel.galleryDetailsListFlow.test {
            //loading
            val loadingEmission = awaitItem()
            assertThat(loadingEmission.isLoading).isTrue()

            //data
            val dataEmission = awaitItem()
            assertThat(dataEmission.galleryDetailsList).isNotNull()
        }
    }
}