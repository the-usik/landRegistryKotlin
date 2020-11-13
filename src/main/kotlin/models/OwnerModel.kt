package models

import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import org.bson.Document
import org.bson.types.ObjectId
import tornadofx.ItemViewModel
import tornadofx.getValue
import tornadofx.onChange
import tornadofx.setValue
import java.time.LocalDate

class Owner() : DatabaseModel {
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

    val birthdayProperty = SimpleObjectProperty<LocalDate>()
    var birthday by birthdayProperty

    constructor(document: Document) : this() {
        updateModel(document)
    }

    override fun toDocument(): Document {
        val document = Document()

        document.append("firstName", firstName)
        document.append("middleName", middleName)
        document.append("fullName", fullName)
        document.append("lastName", lastName)
        document.append("phone", phone)
        document.append("email", email)
        document.append("birthday", birthday)

        return document
    }

    override fun updateModel(document: Document) {
        with(document) {
            id = getObjectId("_id")
            firstName = getString("firstName")
            middleName = getString("middleName")
            lastName = getString("lastName")
            fullName = getString("fullName")
            phone = getString("phone")
            email = getString("email")
            birthday = LocalDate.ofEpochDay(getDate("birthday").time)
        }
    }
}

class OwnerModel : ItemViewModel<Owner>() {
    var id = bind(Owner::idProperty)
    var firstName = bind(Owner::firstNameProperty)
    var middleName = bind(Owner::middleNameProperty)
    var lastName = bind(Owner::lastNameProperty)
    var fullName = bind(Owner::fullNameProperty)
    var phone = bind(Owner::phoneProperty)
    var email = bind(Owner::emailProperty)
    var birthday = bind(Owner::birthdayProperty)

    init {
        bindOnChange(firstName, middleName, lastName) {
            fullName.value = getFullName()
        }
    }

    private fun getFullName(): String {
        return "${firstName.value ?: ""} ${middleName.value ?: ""} ${lastName.value ?: ""}"
    }

    private fun bindOnChange(vararg properties: SimpleStringProperty, block: () -> Unit) {
        for (property in properties) {
            property.onChange { block() }
        }
    }
}