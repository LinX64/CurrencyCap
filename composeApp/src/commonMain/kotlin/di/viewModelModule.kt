package di

import org.koin.dsl.module
import ui.screens.MainViewModel
import ui.screens.ai_predict.BookmarksViewModel
import ui.screens.auth.fill_profile.FillProfileViewModel
import ui.screens.auth.login.LoginViewModel
import ui.screens.auth.register.RegisterViewModel
import ui.screens.auth.reset_password.ResetPasswordViewModel
import ui.screens.exchange.ExchangeViewModel
import ui.screens.news.NewsViewModel
import ui.screens.news.news_detail.NewsDetailViewModel
import ui.screens.overview.OverviewViewModel
import ui.screens.profile.ProfileViewModel
import ui.screens.search.SearchViewModel
import ui.screens.settings.SettingsViewModel

val viewModelModule = module {
    single { MainViewModel(get()) }
    single { OverviewViewModel(get()) }
    single { ExchangeViewModel(get(), get()) }
    single { BookmarksViewModel() } // TODO
    single { SearchViewModel(get()) }
    single { LoginViewModel(get(), get()) }
    single { RegisterViewModel(get(), get()) }
    single { ProfileViewModel(get(), get()) }
    single { FillProfileViewModel(get()) }
    single { ResetPasswordViewModel(get()) }
    single { NewsViewModel(get()) }
    single { NewsDetailViewModel(get()) }
    single { BookmarksViewModel() } // TODO
    single { SettingsViewModel(get(), get()) } // TODO
}

val previewModule = module {
    single { SettingsViewModel(get(), get()) }
}