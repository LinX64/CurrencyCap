package di

import androidx.lifecycle.SavedStateHandle
import org.koin.dsl.module
import ui.navigation.util.ENCODED_URL
import ui.screens.MainViewModel
import ui.screens.initial.get_verified.GetVerifiedPhoneViewModel
import ui.screens.initial.login.LoginViewModel
import ui.screens.initial.register.RegisterViewModel
import ui.screens.initial.reset_password.ResetPasswordViewModel
import ui.screens.main.ai_predict.AiPredictViewModel
import ui.screens.main.bookmarks.BookmarksViewModel
import ui.screens.main.exchange.ExchangeViewModel
import ui.screens.main.news.NewsViewModel
import ui.screens.main.news.news_detail.NewsDetailViewModel
import ui.screens.main.overview.OverviewViewModel
import ui.screens.main.profile.ProfileViewModel
import ui.screens.main.search.SearchViewModel
import ui.screens.main.settings.SettingsViewModel

val viewModelModule = module {
    single { MainViewModel(get()) }
    single { OverviewViewModel(get()) }
    single { ExchangeViewModel(get(), get(), get()) }
    single { BookmarksViewModel(get()) }
    single { SearchViewModel(get()) }
    single { LoginViewModel(get(), get()) }
    single { RegisterViewModel(get(), get()) }
    single { ProfileViewModel(get(), get()) }
    single { GetVerifiedPhoneViewModel(get()) }
    single { ResetPasswordViewModel(get()) }
    single { NewsViewModel(get(), get()) }
    single { SettingsViewModel(get(), get()) }
    single { AiPredictViewModel() }

    factory { (url: String) -> NewsDetailViewModel(get(), SavedStateHandle(mapOf(ENCODED_URL to url))) }
}

val previewModule = module {
    single { OverviewViewModel(get()) }
}