import styles.MainStyles
import tornadofx.App
import tornadofx.launch
import views.AuthView

class MainApplication : App(AuthView::class, MainStyles::class)

fun main() {
    launch<MainApplication>()
}