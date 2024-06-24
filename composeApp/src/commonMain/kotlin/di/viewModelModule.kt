package di

import org.koin.dsl.module
import ui.screens.ai_predict.AiPredictViewModel
import ui.screens.auth.fill_profile.FillProfileViewModel
import ui.screens.auth.forgot_password.ResetPasswordViewModel
import ui.screens.auth.login.LoginViewModel
import ui.screens.auth.register.RegisterViewModel
import ui.screens.exchange.ExchangeViewModel
import ui.screens.overview.OverviewViewModel
import ui.screens.profile.ProfileViewModel
import ui.screens.search.SearchViewModel

val viewModelModule = module {
    single { OverviewViewModel(get()) }
    single { ExchangeViewModel(get(), get()) }
    single { AiPredictViewModel() }
    single { SearchViewModel(get()) }
    single { LoginViewModel(get(), get()) }
    single { RegisterViewModel(get(), get()) }
    single { ProfileViewModel(get()) }
    single { FillProfileViewModel(get()) }
    single { ResetPasswordViewModel(get()) }
}

val previewModule = module {
    single { RegisterViewModel(get(), get()) }
}