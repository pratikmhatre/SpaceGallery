package com.cypher.spacegallery.gallery_details.presentation.events

sealed class UiEvents {
    class ShowErrorMessage(val message: String?) : UiEvents()
}