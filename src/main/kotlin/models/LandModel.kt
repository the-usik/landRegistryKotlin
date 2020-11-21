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
    val categoryIdProperty = SimpleObjectProperty<ObjectId>()
    val ownerIdProperty = SimpleObjectProperty<ObjectId>()
    val addressProperty = SimpleStringProperty()
    val priceProperty = SimpleDoubleProperty()
    val totalAreaProperty = SimpleDoubleProperty()
    val surveyProperty = SimpleBooleanProperty()

    val categoryProperty = SimpleObjectProperty<Category>()
    val ownerProperty = SimpleObjectProperty<Owner>()

    var category by categoryProperty
    var owner by ownerProperty

    var id by idProperty
    var ownerId by ownerIdProperty
    var categoryId by categoryIdProperty
    var address by addressProperty
    var price by priceProperty
    var totalArea by totalAreaProperty
    var survey by surveyProperty

    init {
        owner = Owner()
        category = Category()
    }

    constructor(document: Document) : this() {
        updateModel(document)
    }

    override fun toDocument(): Document {
        val document = Document()

        document.append("ownerId", owner?.id)
        document.append("categoryId", category?.id)
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

class LandModel : ItemViewModel<Land>(Land()) {
    var id = bind(Land::idProperty)
    var ownerId = bind(Land::ownerIdProperty)
    var categoryId = bind(Land::categoryIdProperty)
    var address = bind(Land::addressProperty)
    var price = bind(Land::priceProperty)
    var totalArea = bind(Land::totalAreaProperty)
    var survey = bind(Land::surveyProperty)

    var owner = bind(Land::ownerProperty)
    val category = bind(Land::categoryProperty)
}