package com.wizeline.academy.testing.ui.home

import androidx.annotation.StringRes
import com.wizeline.academy.testing.R

enum class FilterOption(@StringRes val label: Int) {
    ALL(R.string.label_filter_option_all),
    FAVORITES(R.string.label_filter_option_favorites)
}