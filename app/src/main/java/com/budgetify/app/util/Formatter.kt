package com.budgetify.app.util

import android.annotation.SuppressLint
import kotlin.math.roundToInt

@SuppressLint("DefaultLocale")
fun Double.toFormattedAmount(): String {
    val numString = String.format("%,d", roundToInt())
    return "R$numString"
}

fun Float.toFormattedPercentage(): String {
    return (this * 100).roundToInt().toString() + "%"
}
