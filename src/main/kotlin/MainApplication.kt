import styles.MainStyles
import tornadofx.App
import tornadofx.launch
import views.AuthView
import views.MainView
import java.util.logging.Level
import java.util.logging.Logger

class MainApplication : App(MainView::class, MainStyles::class)

fun main() {
    val mongoLogger = Logger.getLogger("org.mongodb.driver")
    mongoLogger.level = Level.SEVERE

    launch<MainApplication>()
}