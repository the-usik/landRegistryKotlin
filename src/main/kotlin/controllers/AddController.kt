package controllers

import database.Database
import models.Land
import models.LandModel
import models.Owner
import models.OwnerModel
import tornadofx.Controller

class AddController : Controller() {
    val categories = Database.getCategories()
    val landModel = LandModel()
    val ownerModel = OwnerModel()

    init {
        clearForm()
    }

    fun submitForm() {
        if (ownerModel.commit()) {
            Database.insertOwner(ownerModel.item).run {
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