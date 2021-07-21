package ru.skillbranch.devintensive.extensions

import ru.skillbranch.devintensive.utils.Utils.makePlural
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.absoluteValue

const val SECOND = 1000L
const val MINUTE = 60 * SECOND
const val HOUR = 60 * MINUTE
const val DAY = 24 * HOUR

fun Date.format(pattern: String = "HH:mm:ss dd.MM.yy"): String {
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}

fun Date.add(value: Int, units: TimeUnits = TimeUnits.SECOND): Date {
    this.time += when (units) {
        TimeUnits.SECOND -> value * SECOND
        TimeUnits.MINUTE -> value * MINUTE
        TimeUnits.HOUR -> value * HOUR
        TimeUnits.DAY -> value * DAY
    }
    return this
}

fun Date.humanizeDiff(date: Date = Date()): String {
    val diff = date.time - time
    return when {
        diff < -360 * DAY -> "более чем через год"
        diff < -26 * HOUR -> "через ${TimeUnits.DAY.plural(diff.absoluteValue / DAY)}"
        diff < -22 * HOUR -> "через день"
        diff < -75 * MINUTE -> "через ${TimeUnits.HOUR.plural(diff.absoluteValue / HOUR)}"
        diff < -45 * MINUTE -> "через час"
        diff < -75 * SECOND -> "через ${TimeUnits.MINUTE.plural(diff.absoluteValue / MINUTE)}"
        diff < -45 * SECOND -> "через минуту" // -75..-45
        diff < -SECOND -> "через несколько секунд" // -1..0
        diff <= SECOND -> "только что" // 0 - 1
        diff <= 45 * SECOND -> "несколько секунд назад" // 1-45
        diff <= 75 * SECOND -> "минуту назад" // 45-75
        diff <= 45 * MINUTE -> "${TimeUnits.MINUTE.plural(diff / MINUTE)} назад"
        diff <= 75 * MINUTE -> "час назад"
        diff <= 22 * HOUR -> "${TimeUnits.HOUR.plural(diff / HOUR)} назад"
        diff <= 26 * HOUR -> "день назад"
        diff <= 360 * DAY -> "${TimeUnits.DAY.plural(diff / DAY)} назад"
        else -> "более года назад"
    }
}

enum class TimeUnits {
    SECOND {
        override fun plural(value: Long) = makePlural(value, listOf("секунду", "секунды", "секунд"))
    },
    MINUTE {
        override fun plural(value: Long) = makePlural(value, listOf("минуту", "минуты", "минут"))
    },
    HOUR {
        override fun plural(value: Long) = makePlural(value, listOf("час", "часа", "часов"))
    },
    DAY {
        override fun plural(value: Long) = makePlural(value, listOf("день", "дня", "дней"))
    };

    abstract fun plural(value: Long): String
}
