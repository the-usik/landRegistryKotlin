package views.pages

import database.Database
import events.DeleteConfirmationEvent
import events.EditPageEvent
import javafx.geometry.Orientation
import javafx.geometry.Pos
import javafx.scene.layout.VBox
import models.Land
import styles.MainStyles
import tornadofx.*
import helpers.nullDecorator
import models.LandModel
import scopes.Scopes

class GeneralView : View() {
    private val lands = Database.getLands()
    private val selectedLand: LandModel by inject(Scopes.landScope)
    private val viewContainer by cssid()

    override val root = vbox {
        listmenu(orientation = Orientation.HORIZONTAL) {
            alignment = Pos.CENTER
            spacing = 10.0
            item("list") {
                whenSelected { }
            }
            item("table")

        }

        vbox {
            setId(viewContainer)
            // list view
            // table view
            
        }

        vbox(spacing = 20) {
            for (land in lands) {
                landBlock(land)
            }
        }
    }


    private fun selectLand(land: Land) {
        selectedLand.item = land
    }

    private fun VBox.landBlock(land: Land) = hbox(spacing = 20) {
        addClass(MainStyles.block)
        form {
            hbox(spacing = 20) {
                fieldset("Land") {
                    label("Address: ${land.address.nullDecorator()}")
                    label("Price: ${land.price}")
                    label("Category: ${land.category.name.nullDecorator()}")
                    label("Survey: ${land.survey}")
                }

                fieldset("Owner") {
                    label("First name: ${land.owner.firstName.nullDecorator()}")
                    label("Middle name: ${land.owner.middleName.nullDecorator()}")
                    label("Last name: ${land.owner.lastName.nullDecorator()}")
                    label("Phone: ${land.owner.phone.nullDecorator()}")
                    label("Email: ${land.owner.email.nullDecorator()}")
                }
            }
        }

        vbox(spacing = 10) {
            alignment = Pos.CENTER

            button("edit") {
                useMaxSize = true
                action {
                    selectLand(land)
                    fire(EditPageEvent)
                }
            }
            button("remove") {
                useMaxSize = true
                action {
                    selectLand(land)
                    fire(DeleteConfirmationEvent)
                }
            }
        }
    }
}