package com.bear3.gank_kotlin.ui.common.utils

import android.content.Context
import android.util.TypedValue
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

/**
 * Description:
 * Author: TT
 * From: 2019/9/2
 * Last Version: 1.0.0
 * Last Change Time: 2019/9/2
 * ----------- History ---------
 * *-*
 * version:
 * description:
 * time: 2019/9/2
 * *-*
 */
internal fun dp2px(context: Context, dp: Float): Float {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.resources.displayMetrics)
}

internal fun loadImageUrl(context: Context, url: String?, imageView: ImageView, defaultRes: Int = 0) {
    val options = RequestOptions().apply{
        placeholder(defaultRes)
        priority(Priority.NORMAL)
        skipMemoryCache(false)
        diskCacheStrategy(DiskCacheStrategy.RESOURCE)
    }
    val tag = imageView.tag
    imageView.tag = null
    Glide.with(context)
        .load(url)
        .apply(options)
        .thumbnail(0.1f)
        .into(imageView)
    imageView.tag = tag
}

