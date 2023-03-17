package com.cypher.spacegallery.gallery_list.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cypher.spacegallery.core.helpers.Resource
import com.cypher.spacegallery.gallery_list.domain.usecases.GetGalleryList
import com.cypher.spacegallery.gallery_list.presentation.events.GalleryDataState
import com.cypher.spacegallery.gallery_list.presentation.events.UiEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
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
        viewModelScope.launch {
            _galleryDataFlow.emit(GalleryDataState(true))
            val result = getGalleryList()
            when (result) {
                is Resource.Loading -> {
                    _galleryDataFlow.emit(GalleryDataState(true))
                }
                is Resource.Success -> {
                    _galleryDataFlow.emit(GalleryDataState(galleryData = result.data))
                }
                is Resource.Error -> {
                    _galleryDataFlow.emit(GalleryDataState(false))
                    _uiEventsFlow.emit(UiEvents.ShowErrorMessage(result.exception?.message))
                }
            }
        }
    }
}