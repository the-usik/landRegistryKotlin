package helpers

import javafx.scene.Node
import models.Category
import models.LandModel
import models.OwnerModel
import scopes.Scopes
import tornadofx.*

fun Node.createOwnerForm(title: String, ownerModel: OwnerModel) = fieldset(text = title) {
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

fun Node.createLandForm(title: String, landModel: LandModel, categories: List<Category>) = fieldset(text = title) {
    field("Category: ") {
        combobox(
            property = landModel.category,
            values = categories
        ) {
            cellFormat(FX.defaultScope) {
                text = if (it != null) it.name else "none"
            }

            selectionModel.select(
                categories.find { category ->
                    category.name == landModel.category.value?.name
                }
            )
        }.required()
    }
    field("Address: ") { textfield(landModel.address).required() }
    field("Price: ") {
        textfield(landModel.price) {
            numberValidator()
            required()
        }
    }
    field("Total Area: ") {
        textfield(landModel.totalArea) {
            numberValidator()
            required()
        }
    }
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