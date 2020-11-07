package models

import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleDoubleProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import org.bson.Document
import org.bson.types.ObjectId
import tornadofx.ItemViewModel

import tornadofx.getValue
import tornadofx.setValue

class Land() : DatabaseModel {
    val idProperty = SimpleObjectProperty<ObjectId>()
    var id by idProperty

    val ownerIdProperty = SimpleObjectProperty<ObjectId>()
    var ownerId by ownerIdProperty

    val categoryIdProperty = SimpleObjectProperty<ObjectId>()
    var categoryId by categoryIdProperty

    val addressProperty = SimpleStringProperty()
    var address by addressProperty

    val priceProperty = SimpleDoubleProperty()
    var price by priceProperty

    val totalAreaProperty = SimpleDoubleProperty()
    var totalArea by totalAreaProperty

    val surveyProperty = SimpleBooleanProperty()
    var survey by surveyProperty

    constructor(document: Document) : this() {
        updateModel(document)
    }

    override fun toDocument(): Document {
        val document = Document()

        document.append("ownerId", ownerId)
        document.append("categoryId", categoryId)
        document.append("address", address)
        document.append("price", price)
        document.append("totalArea", totalArea)
        document.append("survey", survey)

        return document
    }


    override fun updateModel(document: Document) {
        with(document) {
            id = getObjectId("_id")
            ownerId = getObjectId("ownerId")
            categoryId = getObjectId("categoryId")
            address = getString("address")
            price = getDouble("price")
            totalArea = getDouble("totalArea")
            survey = getBoolean("survey")
        }
    }
}

class LandModel : ItemViewModel<Land>() {
    var id = bind(Land::idProperty)
    var ownerId = bind(Land::ownerIdProperty)
    var categoryId = bind(Land::categoryIdProperty)
    var address = bind(Land::addressProperty)
    var price = bind(Land::priceProperty)
    var totalArea = bind(Land::totalAreaProperty)
    var survey = bind(Land::surveyProperty)
}