package views

import events.EditPageEvent
import events.DeleteConfirmationEvent
import extensions.selectItem
import javafx.geometry.Pos
import javafx.scene.layout.AnchorPane
import javafx.scene.layout.Priority
import javafx.stage.Modality
import scopes.Scopes
import styles.MainStyles
import tornadofx.*
import views.pages.*

class MainView : View("Main page") {
    private val listMenu = createListMenu()

    override val root = borderpane {
        setId(MainStyles.rootContainer)

        top = hbox {
            alignment = Pos.CENTER
            padding = insets(20.0)
            label(title)
        }
        left = vbox {
            vgrow = Priority.ALWAYS
            alignment = Pos.CENTER
            add(listMenu)
        }
        center = anchorpane {
            setId(MainStyles.mainContent)
        }
    }

    override fun onDock() {
        listMenu.selectItem(0)
    }

    init {
        setWindowProperties()
        addEventHandlers()
    }

    private fun setWindowProperties() {
        currentStage?.isResizable = false
        currentStage?.width = 1080.0
        currentStage?.height = 720.0
    }

    private fun addEventHandlers() {
        subscribe<EditPageEvent> {
            this@MainView.openInternalWindow(
                scope = Scopes.landScope,
                view = EditFormView::class
            )
        }

        subscribe<DeleteConfirmationEvent> {
            this@MainView.openInternalWindow(
                scope = Scopes.landScope,
                view = DeleteConfirmationView::class
            )
        }
    }

    private fun createListMenu() = listmenu {
        addClass(MainStyles.listMenu)

        item(text = "General") {
            whenSelected { switchPage<GeneralView>() }
        }
        item(text = "Add land") {
            whenSelected { switchPage<AddFormView>() }
        }
        item(text = "Settings") {
            whenSelected { switchPage<SettingsView>() }
        }
        item(text = "About") {
            whenSelected { switchPage<AboutView>() }
        }
    }

    private inline fun <reified T : View> switchPage() {
        root.select<AnchorPane>(MainStyles.mainContent).replaceChildren {
            add(T::class)
        }
    }
}