package com.budgetify.app.util

import kotlin.math.roundToInt

fun Double.toFormattedAmount(): String {
    val numString = String.format("%,d", roundToInt())
    return "R $numString"
}
