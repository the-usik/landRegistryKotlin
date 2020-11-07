package views.pages

import javafx.collections.FXCollections
import scopes.AddScope
import tornadofx.*

class AddFormView : View() {
    override val scope = AddScope()
    private val landOwnerModel = scope.ownerModel
    private val landModel = scope.landModel

    override val root = form {
        hbox(spacing = 20) {
            fieldset(text = "New owner") {
                field("First name: ") { textfield(landOwnerModel.firstName).required() }
                field("Middle name: ") { textfield(landOwnerModel.middleName) }
                field("Last name: ") { textfield(landOwnerModel.lastName) }
                field("Full name: ") { textfield(landOwnerModel.fullName).isDisable = true }
                field("Phone: ") { textfield(landOwnerModel.phone) }
                field("Email: ") {
                    textfield(landOwnerModel.email)
                }
                field("Birthday: ") {
                    datepicker(landOwnerModel.birthday)
                }
            }

            fieldset(text = "New land") {
                field("Category: ") {
                    combobox(
                        property = landModel.categoryId,
                        values = FXCollections.observableArrayList()
                    ).required()
                }
                field("Address: ") { textfield(landModel.address).required() }
                field("Price: ") { textfield(landModel.price).required() }
                field("Total Area: ") { textfield(landModel.totalArea).required() }
                field("survey: ") {
                    togglebutton(selectFirst = false) {
                        val selectedText = selectedProperty().stringBinding {
                            if (isSelected) "completed" else "not completed"
                        }
                        textProperty()?.bind(selectedText)
                    }
                }
            }
        }

        button("add") {
            //enableWhen(landOwnerModel.valid)
            minWidth = 200.0

            action {
                processAddOwnerAsync()
            }
        }
    }

    private fun processAddOwnerAsync() {
        landOwnerModel.commit()
        val owner = landOwnerModel.item
        println(owner.firstName)
        println(owner.lastName)
        println(owner.phone)
//        runAsync { Database.insertLandOwner(landOwnerModel.item!!) } ui { response -> println(response.insertedId) }
    }
}