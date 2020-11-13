import styles.MainStyles
import tornadofx.App
import tornadofx.launch
import views.MainView

class MainApplication : App(MainView::class, MainStyles::class)

fun main() {
    launch<MainApplication>()
}