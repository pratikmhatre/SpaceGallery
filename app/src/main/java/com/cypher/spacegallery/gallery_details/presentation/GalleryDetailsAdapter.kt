package com.cypher.spacegallery.gallery_details.presentation

import android.graphics.Color
import android.graphics.drawable.Animatable
import android.transition.TransitionManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.controller.BaseControllerListener
import com.facebook.drawee.view.SimpleDraweeView
import com.facebook.imagepipeline.image.ImageInfo
import com.facebook.imagepipeline.request.ImageRequest
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.cypher.spacegallery.databinding.ItemGalleryDetailsBinding
import com.cypher.spacegallery.databinding.ItemGalleryListBinding
import com.cypher.spacegallery.gallery_details.domain.models.GalleryDetailsItem
import javax.inject.Inject

class GalleryDetailsAdapter @Inject constructor() :
    RecyclerView.Adapter<GalleryDetailsAdapter.GalleryItemHolder>() {

    private val itemCallbacks = object : DiffUtil.ItemCallback<GalleryDetailsItem>() {
        override fun areItemsTheSame(
            oldItem: GalleryDetailsItem,
            newItem: GalleryDetailsItem
        ) = oldItem.imageUrl == newItem.imageUrl

        override fun areContentsTheSame(oldItem: GalleryDetailsItem, newItem: GalleryDetailsItem) =
            oldItem.imageUrl == newItem.imageUrl

    }
    private var backpressListener: (() -> Unit)? = null
    private val listDiffer = AsyncListDiffer(this, itemCallbacks)

    var galleryList: List<GalleryDetailsItem>
        get() = listDiffer.currentList
        set(value) = listDiffer.submitList(value)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryItemHolder {
        val binding =
            ItemGalleryDetailsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GalleryItemHolder(binding)
    }

    override fun onBindViewHolder(holder: GalleryItemHolder, position: Int) {
        holder.bindItem(galleryList[position])
    }

    override fun getItemCount() = listDiffer.currentList.size

    inner class GalleryItemHolder(private val itmeGalleryDetailsBinding: ItemGalleryDetailsBinding) :
        RecyclerView.ViewHolder(itmeGalleryDetailsBinding.root) {
        fun bindItem(galleryDetailsItem: GalleryDetailsItem) {
            itmeGalleryDetailsBinding.apply {

                ivGallery.populateImage(galleryDetailsItem.imageUrl, galleryDetailsItem.hdUrl)
                tvToolbarTitle.text = galleryDetailsItem.title
                tvTitle.text = galleryDetailsItem.title
                tvDetails.text = galleryDetailsItem.explanation
                tvDate.text = galleryDetailsItem.date


                btnBack.setOnClickListener {
                    backpressListener?.let {action ->
                        action()
                    }
                }
            }
        }
    }

    private fun SimpleDraweeView.populateImage(imageUrl: String, hdImageUrl: String) {
        val controllerListener = object : BaseControllerListener<ImageInfo>() {
            override fun onFinalImageSet(
                id: String?,
                imageInfo: ImageInfo?,
                animatable: Animatable?
            ) {
                super.onFinalImageSet(id, imageInfo, animatable)
                imageInfo?.let {
                    this@populateImage.aspectRatio = it.width.toFloat() / it.height
                }
            }
        }
        val controller =
            Fresco.newDraweeControllerBuilder().setControllerListener(controllerListener)
                .setLowResImageRequest(
                    ImageRequest.fromUri(imageUrl)
                ).setUri(imageUrl).build()

        this.controller = controller
    }

    fun setOnBackPressListener(listener: () -> Unit) {
        backpressListener = listener
    }
}