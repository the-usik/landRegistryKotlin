package models

import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import org.bson.Document
import org.bson.types.ObjectId
import tornadofx.ItemViewModel

import tornadofx.getValue
import tornadofx.setValue

class User(document: Document) : DatabaseModel {
    val idProperty = SimpleObjectProperty<ObjectId>()
    var id by idProperty

    val loginProperty = SimpleStringProperty()
    var login by loginProperty

    val passwordProperty = SimpleStringProperty()
    var password by passwordProperty

    init {
        updateModel(document)
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