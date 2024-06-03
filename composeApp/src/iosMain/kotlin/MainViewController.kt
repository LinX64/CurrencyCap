import androidx.compose.ui.window.ComposeUIViewController
import ui.App

fun MainViewController() = ComposeUIViewController(
    configure = {
        KoinInitializer().init()
    }
) {
    App()
}