package com.example.mycontacts.model

data class Contact(
    val id: Int,
    val name: String,
    val phone: String,
    val email: String = ""
) {
    // Допоміжна властивість для ініціалів у кружечку (наприклад, "МС" для "Марія Сидоренко")
    val initials: String
        get() = name.trim()
            .split("\\s+".toRegex())
            .filter { it.isNotEmpty() }
            .take(2)
            .joinToString("") { it.first().uppercase() }
            .ifEmpty { "?" }
}