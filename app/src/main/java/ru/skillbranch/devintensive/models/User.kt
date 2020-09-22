package ru.skillbranch.devintensive.models

import java.util.*

data class User(
    val id: String,
    var firstName: String?,
    var lastName: String?,
    var avatar: String?,
    var rating: Int = 0,
    var respect: Int = 0,
    var lastVisit: Date? = Date(),
    var isOnline: Boolean = false
) {
    constructor(id: String, firstName: String?, lastName: String?) : this(
        id,
        firstName,
        lastName,
        null
    )

    constructor(id: String) : this(id, "John", "Doe")

    companion object Factory {
        private var lastId: Int = -1

        fun makeUser(fullName: String?): User {
            ++lastId
            if (fullName.isNullOrBlank()) {
                return User("$lastId", null, null)
            }
            val list = fullName.split(" ")
            val (firstName, lastName) = if (list.size == 2) {
                list[0] to list[1]
            } else {
                null to null
            }
            return User("$lastId", firstName, lastName)
        }
    }
}
