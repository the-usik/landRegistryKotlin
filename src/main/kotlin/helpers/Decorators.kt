package helpers

fun <T> T.nullDecorator(): String {
    return this?.toString() ?: "<unavailable>"
}