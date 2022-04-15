package com.wizeline.academy.testing.utils

import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.wizeline.academy.testing.R

fun ImageView.loadImage(
    url: String?,
    contentDescription: String,
    @DrawableRes placeholder: Int = R.drawable.loading_animation,
    @DrawableRes error: Int = R.drawable.ic_broken_image
) {
    Glide.with(this)
        .load(url)
        .transform(RoundedCorners(40))
        .placeholder(placeholder)
        .error(error)
        .into(this)

    this.contentDescription = contentDescription
    this.tag = url
}