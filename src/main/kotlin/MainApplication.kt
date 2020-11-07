import styles.MainStyles
import tornadofx.App
import views.AuthView

class MainApplication : App(AuthView::class, MainStyles::class)