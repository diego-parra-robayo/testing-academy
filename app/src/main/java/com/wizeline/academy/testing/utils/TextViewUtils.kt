package com.wizeline.academy.testing.utils

import android.widget.TextView
import androidx.annotation.StringRes
import com.wizeline.academy.testing.R

fun TextView.setTextOrDefault(
    text: String,
    @StringRes defaultText: Int = R.string.label_info_not_available
) {
    this.text = text.ifBlank { context.getString(defaultText) }
}