package views

import tornadofx.*

class MainView : View("Main page") {
    override val root = borderpane {
        top = label(title)
        left = listmenu {
            item(text = "general")
            item(text = "add land")
            item(text = "settings")
            item(text = "about")
        }
        center = pane {
            
        }

    }
}