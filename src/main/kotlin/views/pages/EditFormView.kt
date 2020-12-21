package views.pages

import controllers.LandController
import helpers.createLandForm
import helpers.createOwnerForm
import javafx.geometry.Pos
import models.LandModel
import models.OwnerModel
import scopes.LandScope
import tornadofx.*

class EditFormView : Fragment() {
    override val scope = super.scope as LandScope

    private val landModel: LandModel by inject()
    private val ownerModel = OwnerModel().also {
        it.itemProperty.cleanBind(landModel.owner)
    }

    private val controller: LandController by inject()

    override val root = form {
        hbox(spacing = 20) {
            createOwnerForm(title = "Edit owner", ownerModel)
            createLandForm(
                title = "Edit land", landModel,
                categories = controller.categories
            )
        }
        hbox(spacing = 20, alignment = Pos.CENTER_RIGHT) {
            button("save") {
                action {
                    runAsync { controller.editLand(landModel.item) }
                }
            }
            button("cancel") {
                action {
                    close()
                }
            }
        }
    }
}