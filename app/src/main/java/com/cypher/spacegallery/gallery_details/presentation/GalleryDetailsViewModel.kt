package com.cypher.spacegallery.gallery_details.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cypher.spacegallery.core.helpers.Resource
import com.cypher.spacegallery.gallery_details.domain.use_cases.GetGalleryDetailsList
import com.cypher.spacegallery.gallery_details.presentation.events.GalleryDetailsListState
import com.cypher.spacegallery.gallery_details.presentation.events.UiEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GalleryDetailsViewModel @Inject constructor(private val getGalleryDetails: GetGalleryDetailsList) :
    ViewModel() {
    private val _uiEventsFlow = MutableSharedFlow<UiEvents>()
    val uiEventsFlow = _uiEventsFlow.asSharedFlow()

    private val _galleryDetailsListFlow = MutableStateFlow(GalleryDetailsListState(true))
    val galleryDetailsListFlow = _galleryDetailsListFlow.asStateFlow()

    init {
        fetchGalleryDetailsList()
    }

    fun fetchGalleryDetailsList() {
        viewModelScope.launch {
            when (val result = getGalleryDetails()) {
                is Resource.Loading -> _galleryDetailsListFlow.emit(GalleryDetailsListState(true))
                is Resource.Error -> {
                    _galleryDetailsListFlow.emit(GalleryDetailsListState(false))
                    _uiEventsFlow.emit(UiEvents.ShowErrorMessage(result.exception?.message))
                }
                is Resource.Success -> {
                    _galleryDetailsListFlow.emit(GalleryDetailsListState(galleryDetailsList = result.data))
                }
            }
        }
    }
}