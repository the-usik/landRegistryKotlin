package models

import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import org.bson.Document
import org.bson.types.ObjectId
import tornadofx.ItemViewModel

import tornadofx.getValue
import tornadofx.setValue
import java.util.*

class Owner(document: Document) : DatabaseModel {
    val idProperty = SimpleObjectProperty<ObjectId>()
    var id by idProperty

    val firstNameProperty = SimpleStringProperty()
    var firstName by firstNameProperty

    val middleNameProperty = SimpleStringProperty()
    var middleName by middleNameProperty

    val lastNameProperty = SimpleStringProperty()
    var lastName by lastNameProperty

    val fullNameProperty = SimpleStringProperty()
    var fullName by fullNameProperty

    val phoneProperty = SimpleStringProperty()
    var phone by phoneProperty

    val emailProperty = SimpleStringProperty()
    var email by emailProperty

    val birthdayProperty = SimpleObjectProperty<Date>()
    var birthday by birthdayProperty

    init {
        updateModel(document)
    }

    constructor()

    override fun updateModel(document: Document) {
        with(document) {
            id = getObjectId("_id")
            firstName = getString("firstName")
            middleName = getString("middleName")
            lastName = getString("lastName")
            fullName = getString("fullName")
            phone = getString("phone")
            email = getString("email")
            birthday = getDate("birthday")
        }
    }
}

class OwnerModel : ItemViewModel<Owner>() {
    var id = bind(Owner::idProperty)
    var firstName = bind(Owner::firstNameProperty)
    var middleName = bind(Owner::middleNameProperty)
    var lastName = bind(Owner::lastNameProperty)
    var fullName = bind(Owner::fullNameProperty)
}