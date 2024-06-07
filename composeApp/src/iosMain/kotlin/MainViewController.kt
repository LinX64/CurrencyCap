import androidx.compose.ui.window.ComposeUIViewController
import ui.App

fun MainViewController() = ComposeUIViewController(
    configure = {
        di.KoinInitializer().init()
    }
) {
    App()
}