package com.example.knotty.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Knot (
    @StringRes val nameRes: Int,
    @StringRes val descriptionRes: Int,
    @DrawableRes val howToTieRes: Int,
    val category: KnotCategory
)

enum class KnotCategory {
    LineToHook, LineToLine, Loop
}