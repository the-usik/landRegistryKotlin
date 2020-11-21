package helpers

import javafx.scene.control.TextInputControl
import tornadofx.required
import tornadofx.validator

fun TextInputControl.numberValidator() {
    validator {
        when {
            it.isNullOrBlank() -> error()
            !RegexPatterns.UNSIGNED_NUMBER_REGEX.matches(it) -> error("Incorrect unsigned number")
            else -> null
        }
    }
}

fun TextInputControl.nameValidator() {
    validator {
        when {
            it.isNullOrBlank() -> error()
            !RegexPatterns.NAME_REGEX.matches(it) -> error("Incorrect name")
            else -> null
        }
    }
    required()
}

fun TextInputControl.phoneValidator() {
    validator {
        when {
            it.isNullOrBlank() -> error()
            !RegexPatterns.MOBILE_PHONE_REGEX.matches(it) -> error("Incorrect mobile phone")
            else -> null
        }
    }
    required()
}

fun TextInputControl.emailValidator() {
    validator {
        when {
            it.isNullOrBlank() -> error()
            !RegexPatterns.EMAIL_REGEX.matches(it) -> error("Incorrect email address")
            else -> null
        }
    }
}
