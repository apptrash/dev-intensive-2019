package ru.skillbranch.devintensive.extensions

import ru.skillbranch.devintensive.utils.Utils.makePlural
import java.text.SimpleDateFormat
import java.util.*

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
        diff <= SECOND -> "только что"
        diff <= 45 * SECOND -> "несколько секунд назад"
        diff <= 75 * SECOND -> "минуту назад"
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
