package com.cypher.spacegallery.gallery_list.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cypher.spacegallery.core.helpers.Resource
import com.cypher.spacegallery.gallery_list.domain.usecases.GetGalleryList
import com.cypher.spacegallery.gallery_list.presentation.events.GalleryDataState
import com.cypher.spacegallery.gallery_list.presentation.events.UiEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GalleryListViewModel @Inject constructor(private val getGalleryList: GetGalleryList) :
    ViewModel() {
    private val _galleryDataFlow = MutableStateFlow(GalleryDataState())
    val galleryDataFlow = _galleryDataFlow.asStateFlow()

    private val _uiEventsFlow = MutableSharedFlow<UiEvents>()
    val uiEventsFlow = _uiEventsFlow.asSharedFlow()

    init {
        fetchGalleryData()
    }

    fun onGalleryItemClicked(position: Int) {
        viewModelScope.launch { _uiEventsFlow.emit(UiEvents.OpenDetailsPage(position)) }
    }

    fun fetchGalleryData() {
        getGalleryList().onEach {
            when (it) {
                is Resource.Loading -> _galleryDataFlow.emit(GalleryDataState(isLoading = true))
                is Resource.Error -> {
                    _galleryDataFlow.emit(GalleryDataState(isLoading = false))
                    _uiEventsFlow.emit(
                        UiEvents.ShowErrorMessage(
                            it.exception?.message ?: "Something went wrong"
                        )
                    )
                }
                is Resource.Success -> _galleryDataFlow.emit(GalleryDataState(galleryData = it.data))
            }
        }.launchIn(viewModelScope)
    }
}
