package ru.skillbranch.devintensive.utils

import ru.skillbranch.devintensive.models.User

object Utils {
    private fun nullIfBlank(string: String) = if (string.isBlank()) null else string

    fun parseFullName(fullName: String?): Pair<String?, String?> {
        if (fullName.isNullOrBlank()) {
            return null to null
        }
        val list = fullName.split(" ")
        if (list.size > 2 || list.isEmpty()) {
            return null to null
        }
        if (list.size == 1) {
            return nullIfBlank(list[0]) to null
        }
        return nullIfBlank(list[0]) to nullIfBlank(list[1])
    }

    fun transliteration(payload: String, divider: String = " "): String {
        val dictionary = mapOf(
            "а" to "a",
            "б" to "b",
            "в" to "v",
            "г" to "g",
            "д" to "d",
            "е" to "e",
            "ё" to "e",
            "ж" to "zh",
            "з" to "z",
            "и" to "i",
            "й" to "i",
            "к" to "k",
            "л" to "l",
            "м" to "m",
            "н" to "n",
            "о" to "o",
            "п" to "p",
            "р" to "r",
            "с" to "s",
            "т" to "t",
            "у" to "u",
            "ф" to "f",
            "х" to "h",
            "ц" to "c",
            "ч" to "ch",
            "ш" to "sh",
            "щ" to "sh'",
            "ъ" to "",
            "ы" to "i",
            "ь" to "",
            "э" to "e",
            "ю" to "yu",
            "я" to "ya",
        )
        var p = payload
        for ((k, v) in dictionary) {
            p = p.replace(k, v)
        }
        return p
    }

    fun toInitials(firstName: String?, lastName: String?): String? {
        if (firstName.isNullOrBlank() || lastName.isNullOrBlank()) {
            return null
        }
        return "${firstName.first()} ${lastName.first()}"
    }
}
