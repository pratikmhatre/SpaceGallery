package com.cypher.spacegallery.core.helpers

import android.content.Context
import android.util.DisplayMetrics
import java.text.SimpleDateFormat
import java.util.*

object Utils {
    fun dpToPx(context: Context?, dp: Int): Int {
        if (context == null)
            return 0
        val displayMetrics = context.resources.displayMetrics
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT))
    }
}