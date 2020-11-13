package controllers

import database.Database
import javafx.beans.property.SimpleObjectProperty
import models.Land
import models.LandModel
import models.Owner
import models.OwnerModel
import org.bson.BsonValue
import org.bson.types.ObjectId
import tornadofx.Controller
import tornadofx.cleanBind

class AddController : Controller() {
    val categories = Database.loadCategories()
    val landModel = LandModel()
    val ownerModel = OwnerModel()

    init {
        clearForm()
    }

    fun submitForm() {
        if (ownerModel.commit()) {
            Database.insertLandOwner(ownerModel.item).run {
                landModel.item.ownerId = insertedId?.asObjectId()?.value
            }
        }

        if (landModel.commit()) {
            Database.insertLand(landModel.item)
        }
    }

    fun clearForm() {
        clearOwnerForm()
        clearLandForm()
    }

    private fun clearOwnerForm() {
        ownerModel.item = Owner()
        ownerModel.clearDecorators()
    }

    private fun clearLandForm() {
        landModel.item = Land()
        landModel.clearDecorators()
    }
}