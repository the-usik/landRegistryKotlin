package utils

object RegexPatterns {
    val MOBILE_PHONE_REGEX = Regex("^(\\+\\d{1,3}[- ]?)?\\d{10}\$")
    val NAME_REGEX = Regex("^[a-zа-я]{2,}\$", RegexOption.IGNORE_CASE)
    val EMAIL_REGEX = Regex("^[a-z0-9_]+\\@[a-z0-9\\.-]+\\.[a-z]{2,}\$", RegexOption.IGNORE_CASE)
}
