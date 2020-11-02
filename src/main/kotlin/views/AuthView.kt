package views

import database.Database
import models.UserModel
import tornadofx.*

class AuthView : View("Authorization") {
    val userModel: UserModel by inject()

    override val root = form {
        fieldset("Authorization") {
            field("Login: ") { textfield(userModel.login).required() }
            field("Password: ") { passwordfield(userModel.password).required() }
            button("auth") {
                enableWhen(userModel.valid)
                useMaxWidth = true
                action {
                    runAsync {
                        Database.tryAuthUser(userModel)
                    } ui { response -> handleAuthResponse(response) }
                }
            }
        }
    }

    private fun handleAuthResponse(response: Boolean) {
        if (response) {
            // success auth
        } else {
            // error auth
        }
    }
}