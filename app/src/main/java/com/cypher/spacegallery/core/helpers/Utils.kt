package com.cypher.spacegallery.core.helpers

import android.content.Context
import android.graphics.Rect
import android.graphics.drawable.Animatable
import android.util.DisplayMetrics
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.cypher.spacegallery.core.Constants
import com.cypher.spacegallery.core.database.entities.GalleryItemTable
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.controller.BaseControllerListener
import com.facebook.drawee.view.SimpleDraweeView
import com.facebook.imagepipeline.image.ImageInfo
import com.facebook.imagepipeline.request.ImageRequest
import java.text.SimpleDateFormat
import java.util.*

object Utils {
    private fun dpToPx(context: Context?, dp: Int): Int {
        if (context == null)
            return 0
        val displayMetrics = context.resources.displayMetrics
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT))
    }

    fun getFormattedDate(date: String): String {
        val inputFormat = SimpleDateFormat(Constants.INPUT_DATE_FORMAT, Locale.getDefault())
        val inputDate = inputFormat.parse(date)
        val outputFormat = SimpleDateFormat(Constants.OUTPUT_DATE_FORMAT, Locale.getDefault())
        return if (inputDate == null) date else outputFormat.format(inputDate)
    }

    fun prependCopyrightSymbol(text: String) = "\u00a9 $text"

    fun getDummyItemList(count: Int): List<GalleryItemTable> {
        val list = List(count) {
            GalleryItemTable(
                id = it.toLong(),
                date = "2023-08-20",
                explanation = "Explanation $it",
                hdUrl = "Hd Url $it",
                copyright = null,
                mediaType = "Type $it",
                serviceVersion = "Version $it",
                title = "Title $it",
                url = "Url $it",
            )
        }
        return list
    }

    fun SimpleDraweeView.populateImageUrl(url: String) {
        val controllerListener = object : BaseControllerListener<ImageInfo>() {
            override fun onFinalImageSet(
                id: String?,
                imageInfo: ImageInfo?,
                animatable: Animatable?
            ) {
                super.onFinalImageSet(id, imageInfo, animatable)
                imageInfo?.let {
                    this@populateImageUrl.aspectRatio = it.width.toFloat() / it.height
                }
            }
        }
        val controller =
            Fresco.newDraweeControllerBuilder().setControllerListener(controllerListener)
                .setUri(url).build()
        this.controller = controller
    }

    fun getGridItemDecoration(context: Context): RecyclerView.ItemDecoration {
        return object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                val childAdapterPosition = parent.getChildAdapterPosition(view)

                if (childAdapterPosition == 0 || childAdapterPosition == 1) {
                    outRect.top = dpToPx(context, Constants.GRID_SPACING)
                }
                if (childAdapterPosition % 2 == 0) {
                    outRect.apply {
                        left = dpToPx(context, Constants.GRID_SPACING)
                        right = dpToPx(context, Constants.GRID_SPACING / 2)
                    }
                } else {
                    outRect.apply {
                        right = dpToPx(context, Constants.GRID_SPACING)
                        left = dpToPx(context, Constants.GRID_SPACING / 2)
                    }
                }
                outRect.bottom = dpToPx(context, Constants.GRID_SPACING)

            }
        }
    }

}