package ru.skillbranch.devintensive.extensions

import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs

fun Date.format(pattern: String = "HH:mm:ss dd.MM.yy") =
    SimpleDateFormat(pattern, Locale("ru")).format(this)

fun Date.add(value: Int, units: TimeUnits = TimeUnits.SECOND): Date {
    val date = Date()
    date.time = this.time + value * when (units) {
        TimeUnits.SECOND -> 1L
        TimeUnits.MINUTE -> value * 60L
        TimeUnits.HOUR -> value * 60 * 60L
        TimeUnits.DAY -> value * 24 * 60 * 60L
    }
    return date
}

fun Date.humanizeDiff(): String {
    return when (val timeDiff = abs(Date().time - this.time)) {
        in (0..1) -> "только что"
        in (1..45) -> "несколько секунд назад"
        in (45..75) -> "минуту назад"
        in (75..45 * 60) -> "${timeDiff/60} минут назад"
        in (45 * 60..75 * 60) -> "час назад"
        in (75 * 60..22 * 3600) -> "${timeDiff/3600} часов назад"
        in (22 * 60 * 60..26 * 3600) -> "день назад"
        in (26 * 60 * 60..360 * 24 * 3600) -> "${timeDiff/(3600 * 24)} дней назад"
        else -> "более года назад"
    }
}


enum class TimeUnits { SECOND, MINUTE, HOUR, DAY }
