import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.window.ComposeUIViewController
import ui.App
import util.ApplicationComponent

@OptIn(ExperimentalMaterial3Api::class)
fun MainViewController() = ComposeUIViewController {
    App()
}

fun initialize() {
    ApplicationComponent.init()
}