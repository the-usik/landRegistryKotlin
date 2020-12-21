package views.pages

import models.LandModel
import scopes.LandScope
import tornadofx.*

class DeleteConfirmationView : Fragment() {
    override val scope = super.scope as LandScope

    private val selectedLand: LandModel by inject()

    override val root = form {
        textfield(selectedLand.address)

        label("Remove item?")

        button("Yes") {
            action {
                println("Removing...")
            }

        }
    }

}