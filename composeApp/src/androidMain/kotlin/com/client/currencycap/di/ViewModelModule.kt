import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import ui.screens.MainViewModel
import ui.screens.SearchViewModel

actual val viewModelModule = module {
    viewModelOf(::MainViewModel)
    viewModelOf(::SearchViewModel)
}