package views.pages

import controllers.AddController
import database.Database
import javafx.geometry.Pos
import javafx.scene.layout.HBox
import scopes.AddScope
import tornadofx.*
import utils.emailValidator
import utils.nameValidator
import utils.phoneValidator
import java.time.LocalDate

class AddFormView : View() {
    override val scope = AddScope()

    private val controller: AddController by inject(scope)
    private val landModel = controller.landModel
    private val ownerModel = controller.ownerModel

    override val root = form {
        hbox(spacing = 20) {
            createAddOwnerForm()
            createAddLandForm()
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

    private fun HBox.createAddOwnerForm() = fieldset(text = "New owner") {
        field("First name: ") {
            textfield(ownerModel.firstName).nameValidator()
        }
        field("Middle name: ") {
            textfield(ownerModel.middleName).nameValidator()
        }
        field("Last name: ") {
            textfield(ownerModel.lastName).nameValidator()
        }
        field("Full name: ") {
            textfield(ownerModel.fullName).isDisable = true
        }
        field("Phone: ") {
            textfield(ownerModel.phone).phoneValidator()
        }
        field("Email: ") {
            textfield(ownerModel.email).emailValidator()
        }
        field("Birthday: ") {
            datepicker(ownerModel.birthday).required()
        }
    }

    private fun HBox.createAddLandForm() = fieldset(text = "New land") {
        field("Category: ") {
            combobox(values = controller.categories) {
                cellFormat { text = it.name }
                selectionModel.selectFirst()
                selectionModel.selectedItemProperty().onChange {
                    landModel.categoryId.value = it?.id
                }
            }
        }
        field("Address: ") { textfield(landModel.address).required() }
        field("Price: ") { textfield(landModel.price).required() }
        field("Total Area: ") { textfield(landModel.totalArea).required() }
        field("Survey: ") {
            togglebutton(selectFirst = false) {
                textProperty().bind(
                    selectedProperty().stringBinding {
                        if (isSelected) "completed" else "not completed"
                    }
                )
                selectedProperty().bindBidirectional(landModel.survey)
            }
        }
    }
}