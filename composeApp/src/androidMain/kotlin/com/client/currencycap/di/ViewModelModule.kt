import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import ui.screens.MainViewModel

actual val viewModelModule = module {
    viewModelOf(::MainViewModel)
}