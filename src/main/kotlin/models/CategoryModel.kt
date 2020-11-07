package models

import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import org.bson.Document
import org.bson.types.ObjectId
import tornadofx.getValue
import tornadofx.setValue

class CategoryModel : DatabaseModel {
    val idProperty = SimpleObjectProperty<ObjectId>()
    var id by idProperty

    val nameProperty = SimpleStringProperty()
    var name by nameProperty

    val descriptionProperty = SimpleStringProperty()
    var description by descriptionProperty

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

