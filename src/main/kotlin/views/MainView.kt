package views

import javafx.geometry.Pos
import javafx.scene.layout.AnchorPane
import javafx.scene.layout.BorderPane
import javafx.scene.layout.Priority
import javafx.scene.paint.Color
import javafx.scene.shape.ArcType
import styles.MainStyles
import tornadofx.*
import views.pages.AboutView
import views.pages.AddFormView
import views.pages.GeneralView
import views.pages.SettingsView

class MainView : View("Main page") {
    override val root = borderpane {
        setId(MainStyles.rootContainer)

        top = hbox {
            alignment = Pos.CENTER
            padding = insets(20.0)
            label("Main Page")
        }
        left = vbox {
            vgrow = Priority.ALWAYS
            add(createListMenu())
        }
        center = anchorpane {
            setId(MainStyles.mainContent)
        }
    }

    init {
        currentWindow?.width = 720.0
        currentWindow?.height = 480.0
    }

    override fun onDock() {
        switchPage<GeneralView>()
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