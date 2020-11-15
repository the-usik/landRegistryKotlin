package views

import javafx.scene.layout.AnchorPane
import tornadofx.*
import views.pages.AboutView
import views.pages.AddFormView
import views.pages.GeneralView
import views.pages.SettingsView

class MainView : View("Main page") {
    private val mainContainer by cssid()

    override val root = borderpane {
        top = label(title)
        left = createListMenu()
        center = anchorpane {
            setId(mainContainer)
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
        item(text = "general") {
            whenSelected { switchPage<GeneralView>() }
        }
        item(text = "add land") {
            whenSelected { switchPage<AddFormView>() }
        }
        item(text = "settings") {
            whenSelected { switchPage<SettingsView>() }
        }
        item(text = "about") {
            whenSelected { switchPage<AboutView>() }
        }
    }

    private inline fun <reified T : View> switchPage() {
        root.select<AnchorPane>(mainContainer).replaceChildren {
            add(T::class)
        }
    }
}