package views.pages

import controllers.LandController
import database.Database
import javafx.geometry.Pos
import tornadofx.*
import helpers.*
import models.Land
import models.LandModel
import models.Owner
import models.OwnerModel
import scopes.Scopes
import styles.MainStyles
import java.time.LocalDate

class AddFormView : View() {
    override val scope = Scopes.addScope

    private val controller: LandController by inject()
    private val landModel = LandModel()
    private val ownerModel = OwnerModel().also { it.itemProperty.bind(landModel.owner) }

    override val root = form {
        addClass(MainStyles.block)
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
                        submitForm()
                    }
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

    private fun submitForm() {
        if (ownerModel.commit() && landModel.commit()) {
            val result = controller.addLand(landModel.item)
            if (result) {
                println("success")
                clearForm()
            } else println("error")
        }
    }

    private fun clearForm() {
        ownerModel.item = Owner()
        landModel.item = Land()
    }
}