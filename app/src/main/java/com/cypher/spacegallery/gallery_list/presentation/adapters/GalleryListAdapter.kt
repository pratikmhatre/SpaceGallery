package com.cypher.spacegallery.gallery_list.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.cypher.spacegallery.databinding.ItemGalleryListBinding
import com.cypher.spacegallery.gallery_list.domain.models.GalleryListItem
import javax.inject.Inject

class GalleryListAdapter @Inject constructor() :
    RecyclerView.Adapter<GalleryListAdapter.GalleryItemHolder>() {
    private val itemCallbacks = object : DiffUtil.ItemCallback<GalleryListItem>() {
        override fun areItemsTheSame(oldItem: GalleryListItem, newItem: GalleryListItem): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: GalleryListItem, newItem: GalleryListItem) = true
    }
    private val listDiffer = AsyncListDiffer(this, itemCallbacks)
    private var itemClickListener: ((position: Int, item: GalleryListItem) -> Unit)? = null

    var galleryList: List<GalleryListItem>
        get() = listDiffer.currentList
        set(value) = listDiffer.submitList(value)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryItemHolder {
        val binding =
            ItemGalleryListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GalleryItemHolder(binding)
    }

    override fun onBindViewHolder(holder: GalleryItemHolder, position: Int) {
        holder.bindItem(galleryList[position])
    }

    override fun getItemCount() = listDiffer.currentList.size

    inner class GalleryItemHolder(private val itemGalleryListBinding: ItemGalleryListBinding) :
        RecyclerView.ViewHolder(itemGalleryListBinding.root) {
        fun bindItem(galleryListItem: GalleryListItem) {
            itemGalleryListBinding.apply {
                ivGallery.setImageURI(galleryListItem.url)

                tvTitle.text = galleryListItem.title
                root.setOnClickListener {
                    itemClickListener?.run {
                        this(adapterPosition, galleryListItem)
                    }
                }
            }
        }
    }

    fun setOnItemClickListener(callback: (Int, GalleryListItem) -> Unit) {
        itemClickListener = callback
    }
}