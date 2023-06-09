package com.cypher.spacegallery.gallery_list.presentation.events

sealed class UiEvents {
    data class ShowErrorMessage(val message: String?) : UiEvents()
    data class OpenDetailsPage(val position: Int) : UiEvents()
}