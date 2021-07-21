package ru.skillbranch.devintensive.utils

object Utils {
    private val translilerationDictionary = mapOf(
        'а' to "a",
        'б' to "b",
        'в' to "v",
        'г' to "g",
        'д' to "d",
        'е' to "e",
        'ё' to "e",
        'ж' to "zh",
        'з' to "z",
        'и' to "i",
        'й' to "i",
        'к' to "k",
        'л' to "l",
        'м' to "m",
        'н' to "n",
        'о' to "o",
        'п' to "p",
        'р' to "r",
        'с' to "s",
        'т' to "t",
        'у' to "u",
        'ф' to "f",
        'х' to "h",
        'ц' to "c",
        'ч' to "ch",
        'ш' to "sh",
        'щ' to "sh'",
        'ъ' to "",
        'ы' to "i",
        'ь' to "",
        'э' to "e",
        'ю' to "yu",
        'я' to "ya"
    )

    fun parseFullName(fullName: String?): Pair<String?, String?> {
        if (fullName.isNullOrBlank()) {
            return null to null
        }
        val parts = fullName.split(' ')
        val firstName = parts.getOrNull(0)
        val lastName = parts.getOrNull(1)
        return firstName to lastName
    }

    fun transliteration(payload: String, divider: String = " "): String {
        val builder = StringBuilder()
        for (character in payload) {
            val transliteratedCharacter = translilerationDictionary[character.toLowerCase()]
            if (transliteratedCharacter != null) {
                builder.append(if (character.isLowerCase()) transliteratedCharacter else transliteratedCharacter.capitalize())
                continue
            }
            builder.append(
                if (character.isWhitespace()) {
                    divider
                } else {
                    character
                }
            )
        }
        return builder.toString()
    }

    fun toInitials(firstName: String?, lastName: String?): String? {
        val firstLetter = firstName?.firstOrNull { it.isLetter() }
        val secondLetter = lastName?.firstOrNull { it.isLetter() }
        var initials = ""
        if (firstLetter != null) initials += firstLetter.toUpperCase()
        if (secondLetter != null) initials += secondLetter.toUpperCase()
        return if (initials.isEmpty()) null else initials
    }

    fun makePlural(value: Long, titles: List<String>): String {
        require(titles.size == 3)
        val index = if (value % 100 in 5..19) {
            2
        } else {
            listOf(2, 0, 1, 1, 1, 2)[minOf(value % 10, 5L).toInt()]
        }
        return "$value ${titles[index]}"
    }
}
