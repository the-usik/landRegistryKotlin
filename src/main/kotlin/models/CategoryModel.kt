package models

import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import org.bson.Document
import org.bson.types.ObjectId
import tornadofx.ItemViewModel
import tornadofx.getValue
import tornadofx.setValue

class Category() : DatabaseModel {
    val idProperty = SimpleObjectProperty<ObjectId>()
    val nameProperty = SimpleStringProperty()
    val descriptionProperty = SimpleStringProperty()

    var id by idProperty
    var name by nameProperty
    var description by descriptionProperty

    constructor(document: Document) : this() {
        updateModel(document)
    }

    override fun toDocument(): Document {
        val document = Document()
        document.append("name", name)
        document.append("description", description)

        return document
    }

    override fun updateModel(document: Document) {
        with(document) {
            id = getObjectId("_id")
            name = getString("name")
            description = getString("description")
        }
    }
}

class CategoryModel : ItemViewModel<Category>(Category()) {
    var id = bind(Category::idProperty)
    var name = bind(Category::nameProperty)
    var description = bind(Category::descriptionProperty)
}

