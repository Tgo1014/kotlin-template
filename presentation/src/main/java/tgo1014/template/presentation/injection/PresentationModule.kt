package tgo1014.template.presentation.injection

import org.koin.androidx.experimental.dsl.viewModel
import org.koin.dsl.module
import org.koin.experimental.builder.single
import tgo1014.template.presentation.ui.main.MainActivityViewModel
import tgo1014.template.presentation.ui.sample.SampleViewModel
import tgo1014.template.presentation.ui.splash.SplashViewModel
import tgo1014.template.presentation.util.ViewErrorController


val presentationModule = module {
    viewModel<MainActivityViewModel>()
    viewModel<SampleViewModel>()
    viewModel<SplashViewModel>()
    single<ViewErrorController>()
}