package views

import javafx.scene.layout.AnchorPane
import tornadofx.*
import views.pages.*

class MainView : View("Main page") {
    private val mainContainer by cssid()

    override val root = borderpane {
        top = label(title)
        left = createListMenu()
        center = anchorpane {
            setId(mainContainer)
        }
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