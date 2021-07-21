package ru.skillbranch.devintensive.extensions

fun String.truncate(n: Int = 16): String {
    val truncated = take(n)
    if (length <= n) {
        return truncated
    }
    return truncated.take(
        truncated.length - if (truncated.last().isWhitespace()) {
            1
        } else {
            0
        }
    ) + "..."
}

fun String.stripHtml(): String {
    val trimmed = trim().replace(Regex(" +"), " ")
    return buildString {
        var shouldRemove = false
        for (character in trimmed) {
            if (character == '<') {
                shouldRemove = true
                continue
            } else if (character == '>') {
                shouldRemove = false
                continue
            } else if (character in setOf('&', '\'', '\"')) {
                continue
            }
            if (!shouldRemove) {
                append(character)
            }
        }
    }
}
