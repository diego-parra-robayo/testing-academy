package com.wizeline.academy.testing.utils

fun minutesToHoursAndMinutesString(minutes: Int): String {
    if (minutes <= 0) return ""
    val hours = minutes / 60
    val min = minutes % 60
    return (if (hours > 0) "${hours}h " else "") + "${min}m"
}