package com.cypher.spacegallery.gallery_list.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.cypher.spacegallery.gallery_list.data.network.DummyGalleryListRepository
import com.cypher.spacegallery.gallery_list.domain.usecases.GetGalleryList
import com.cypher.spacegallery.gallery_list.presentation.events.UiEvents
import com.cypher.spacegallery.utils.MainDispatcherRule
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
class GalleryListViewModelTest {
    @get:Rule
    val instantTaskRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: GalleryListViewModel

    @Before
    fun setup() {
        val useCase = GetGalleryList(DummyGalleryListRepository())
        viewModel = GalleryListViewModel(useCase)
    }

    @Test
    fun `fetch gallery data emits the stored list with Success status`() = runTest {
        viewModel.galleryDataFlow.test {
            //initial emission
            awaitItem()

            val firstEmission = awaitItem()
            assertThat(firstEmission.isLoading).isTrue()

            val secondEmission = awaitItem()
            assertThat(secondEmission.galleryData).isNotNull()
        }
        viewModel.fetchGalleryData()
    }

    @Test
    fun `for error scenarios fetch gallery data emits Error status`() = runTest {

        //toggle error simulation
        val dummyRepo = DummyGalleryListRepository()
        dummyRepo.shouldSimulateError(true)

        val useCase = GetGalleryList(dummyRepo)
        val viewModel = GalleryListViewModel(useCase)


        viewModel.galleryDataFlow.test {
            //default emission
            awaitItem()

            //loading emission
            awaitItem()

            val emission = awaitItem()
            assertThat(emission.errorMessage).isNotNull()
        }
        viewModel.fetchGalleryData()
    }

    @Test
    fun `on gallery item clicked UiEvent for opening details page is emmitted`() = runTest {
        val position = 10

        viewModel.onGalleryItemClicked(position)
        viewModel.uiEventsFlow.test {
            val emission = awaitItem()
            assertThat(emission).isInstanceOf(UiEvents.OpenDetailsPage::class.java)
            assertThat((emission as UiEvents.OpenDetailsPage).position).isEqualTo(position)
        }
    }

}