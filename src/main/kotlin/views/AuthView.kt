package views

import database.Database
import javafx.scene.control.Label
import javafx.util.Duration
import models.UserModel
import tornadofx.*

class AuthView : View("Authorization") {
    private val userModel: UserModel by inject()
    private val messageLabelId by cssid()

    override val root = form {
        fieldset("Authorization") {
            field("Login: ") { textfield(userModel.login).required() }
            field("Password: ") { passwordfield(userModel.password).required() }

            label {
                setId(messageLabelId)
                isVisible = false
            }
        }

        button("auth") {
            enableWhen(userModel.valid)
            useMaxWidth = true
            action {
                runAsync {
                    Database.tryAuthUser(userModel.item!!)
                } ui { response -> handleAuthResponse(response) }
            }
        }
    }

    private fun handleAuthResponse(response: Boolean) {
        if (!response) {
            showInlineMessage("Authorization Failed: Incorrect login or password", 2.5.seconds)
            return
        }

        showInlineMessage(
            message = "Success authorization!",
            delay = 1.seconds
        )
        runLater(1.seconds) {
            replaceWith(MainView::class)
        }
    }

    private fun showInlineMessage(message: String, delay: Duration) {
        val labelNode = root.select<Label>(messageLabelId)
        labelNode.text = message
        labelNode.show()

        runLater(delay) { labelNode.hide() }
    }
}