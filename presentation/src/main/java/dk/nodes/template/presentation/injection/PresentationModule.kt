package dk.nodes.template.presentation.injection

import dk.nodes.template.presentation.ui.main.MainActivityViewModel
import dk.nodes.template.presentation.ui.sample.SampleViewModel
import dk.nodes.template.presentation.ui.splash.SplashViewModel
import dk.nodes.template.presentation.util.ViewErrorController
import org.koin.androidx.experimental.dsl.viewModel
import org.koin.dsl.module
import org.koin.experimental.builder.single

val presentationModule = module {
    viewModel<MainActivityViewModel>()
    viewModel<SampleViewModel>()
    viewModel<SplashViewModel>()
    single<ViewErrorController>()
}