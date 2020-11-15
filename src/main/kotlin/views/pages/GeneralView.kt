package views.pages

import database.Database
import javafx.geometry.Orientation
import javafx.geometry.Pos
import javafx.scene.layout.VBox
import models.Land
import tornadofx.*

class GeneralView : View() {
    private val lands = Database.getLands()

    override val root = vbox {
        listmenu(orientation = Orientation.HORIZONTAL) {
            alignment = Pos.CENTER
            item("list")
            item("table")
        }

        vbox {
            for (land in lands) {
                landBlock(land)
            }
        }
    }

    private fun VBox.landBlock(land: Land) = hbox {
        vbox {
            field("") {  }
        }

        vbox {
            alignment = Pos.CENTER
            button("edit")
            button("remove")
        }
        // land info
        // owner info
        // buttons: edit, remove
    }
}