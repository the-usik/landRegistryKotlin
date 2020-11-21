package views.pages

import controllers.AddController
import javafx.geometry.Pos
import scopes.AddScope
import tornadofx.*
import helpers.*
import java.time.LocalDate

class AddFormView : View() {
    override val scope = AddScope()

    private val controller: AddController by inject(scope)
    private val landModel = controller.landModel
    private val ownerModel = controller.ownerModel

    override val root = form {
        hbox(spacing = 20) {
            createOwnerForm("New owner", ownerModel)
            createLandForm("New land", landModel, controller.categories)
        }

        hbox(spacing = 20) {
            alignment = Pos.CENTER
            button("add") {
                isDefaultButton = true
                minWidth = 200.0

                enableWhen(ownerModel.valid.and(landModel.valid))
                action {
                    runAsync {
                        controller.submitForm()
                    } success {
                        println("good")
                    } fail {
                        println("fail")
                    } finally { controller.clearForm() }
                }
            }

            button("auto-fill") {
                minWidth = 200.0

                action {
                    val owner = ownerModel.item!!
                    owner.firstName = "Jack"
                    owner.middleName = "Immanuil"
                    owner.lastName = "Fresco"
                    owner.phone = "+79788634742"
                    owner.email = "me@usik.fresco"
                    owner.birthday = LocalDate.now()
                }
            }
        }
    }

}