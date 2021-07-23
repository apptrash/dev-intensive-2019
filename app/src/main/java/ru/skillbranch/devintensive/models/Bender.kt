package ru.skillbranch.devintensive.models

class Bender(var status: Status = Status.NORMAL, var question: Question = Question.NAME) {
    enum class Status(val color: Triple<Int, Int, Int>) {
        NORMAL(Triple(255, 255, 255)),
        WARNING(Triple(255, 120, 0)),
        DANGER(Triple(255, 60, 60)),
        CRITICAL(Triple(255, 255, 0));

        fun nextStatus(): Status = if (this.ordinal < values().lastIndex) {
            values()[this.ordinal + 1]
        } else {
            values()[0]
        }
    }

    enum class Question(val question: String, val answers: List<String>) {
        NAME("Как меня зовут?", listOf("бендер", "bender")) {
            override fun nextQuestion() = PROFESSION
        },
        PROFESSION("Назови мою профессию?", listOf("сгибальщик", "bender")) {
            override fun nextQuestion() = MATERIAL
        },
        MATERIAL("Из чего я сделан?", listOf("металл", "дерево", "metal", "iron", "wood")) {
            override fun nextQuestion() = BDAY
        },
        BDAY("Когда меня создали?", listOf("2993")) {
            override fun nextQuestion() = SERIAL
        },
        SERIAL("Мой серийный номер?", listOf("2716057")) {
            override fun nextQuestion() = IDLE
        },
        IDLE("На этом всё, вопросов больше нет", listOf()) {
            override fun nextQuestion() = IDLE
        };

        abstract fun nextQuestion(): Question
    }

    fun askQuestion(): String = when (question) {
        Question.NAME -> Question.NAME.question
        Question.PROFESSION -> Question.PROFESSION.question
        Question.MATERIAL -> Question.MATERIAL.question
        Question.BDAY -> Question.BDAY.question
        Question.SERIAL -> Question.SERIAL.question
        Question.IDLE -> Question.IDLE.question
    }

    fun listenAnswer(answer: String): Pair<String, Triple<Int, Int, Int>> {
        val v = validate(answer)
        if (v.isNotEmpty()) {
            return "$v\n${question.question}" to status.color
        }
        return if (question.answers.contains(answer.toLowerCase())) {
            question = question.nextQuestion()
            "Отлично - это правильный ответ!\n${question.question}"
        } else {
            status = status.nextStatus()
            if (status == Status.NORMAL) {
                question = Question.NAME
                "Это неправильный ответ. Давай все по новой"
            } else {
                "Это неправильный ответ!"
            } + "\n${question.question}"
        } to status.color
    }

    private fun validate(answer: String): String {
        return when (question) {
            Question.NAME -> {
                if (answer.isNotBlank() && answer.first().isUpperCase()) ""
                else "Имя должно начинаться с заглавной буквы"
            }
            Question.PROFESSION -> {
                if (answer.isNotBlank() && answer.first().isLowerCase()) ""
                else "Профессия должна начинаться со строчной буквы"
            }
            Question.MATERIAL -> {
                if (!answer.contains(Regex("\\d"))) ""
                else "Материал не должен содержать цифр"
            }
            Question.BDAY -> {
                if (!answer.contains(Regex("\\D"))) ""
                else "Год моего рождения должен содержать только цифры"
            }
            Question.SERIAL -> {
                if (answer.matches(Regex("""^\d{7}$"""))) ""
                else "Серийный номер содержит только цифры, и их 7"
            }
            Question.IDLE -> ""
        }
    }
}
