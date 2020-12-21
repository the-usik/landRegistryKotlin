package views

import database.Database
import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.paint.Color
import javafx.util.Duration
import models.UserModel
import tornadofx.*

class AuthView : View("Authorization") {
    private val userModel: UserModel by inject()
    private val messageLabelId by cssid()

    override val root = borderpane {
        top = hbox {
            alignment = Pos.CENTER
            padding = insets(10.0)
            label("Authorization") {
                textFill = c("#fff")
            }
        }

        center {
            form {
                fieldset {
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
                    action { authUser() }
                }
            }
        }
    }

    init {
        currentStage?.minWidth = 250.0
        currentStage?.minHeight = 300.0
    }

    private fun authUser() {
        if (userModel.commit()) {
            runAsync {
                Database.tryAuthUser(userModel.item)
            } ui { response -> handleAuthResponse(response) }
        } else showInlineMessage(
            message = "Failure on commit",
            delay = 2.5.seconds
        )
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