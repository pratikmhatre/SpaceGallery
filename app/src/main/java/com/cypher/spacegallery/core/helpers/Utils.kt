package com.cypher.spacegallery.core.helpers

import android.content.Context
import android.util.DisplayMetrics
import com.cypher.spacegallery.core.Constants
import java.text.SimpleDateFormat
import java.util.*

object Utils {
    fun dpToPx(context: Context?, dp: Int): Int {
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
}