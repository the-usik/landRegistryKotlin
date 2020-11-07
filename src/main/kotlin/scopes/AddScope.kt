package scopes

import models.Land
import models.LandModel
import models.Owner
import models.OwnerModel
import tornadofx.Scope

class AddScope : Scope() {
    val ownerModel = OwnerModel()
    val landModel = LandModel()

    init {
        ownerModel.item = Owner()
        landModel.item = Land()
    }
}