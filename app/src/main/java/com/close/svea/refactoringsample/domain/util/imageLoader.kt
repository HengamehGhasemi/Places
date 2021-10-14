package com.close.svea.refactoringsample.domain.util

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide

fun imageLoader(context: Context , url : String , imageView: ImageView ){

    Glide.with(context)
        .load(url)
        .into(imageView)
}