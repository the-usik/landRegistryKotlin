package models

import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import org.bson.Document
import org.bson.types.ObjectId
import tornadofx.ItemViewModel

import tornadofx.getValue
import tornadofx.setValue

class User() : DatabaseModel {
    val idProperty = SimpleObjectProperty<ObjectId>()
    var id by idProperty

    val loginProperty = SimpleStringProperty()
    var login by loginProperty

    val passwordProperty = SimpleStringProperty()
    var password by passwordProperty

    constructor(document: Document) : this() {
        updateModel(document)
    }

    override fun toDocument(): Document {
        val document = Document()
        document.append("login", login)
        document.append("password", password)

        return document
    }

    override fun updateModel(document: Document) {
        with(document) {
            id = getObjectId("_id")
            login = getString("login")
            password = getString("password")
        }
    }
}

class UserModel : ItemViewModel<User>() {
    var id = bind(User::idProperty)
    var login = bind(User::loginProperty)
    var password = bind(User::passwordProperty)
}