import androidx.compose.ui.window.ComposeUIViewController
import ui.App
import util.ApplicationComponent

fun MainViewController() = ComposeUIViewController {
    App()
}

fun initialize() {
    ApplicationComponent.init()
}