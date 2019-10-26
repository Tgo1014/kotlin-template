package dk.nodes.template.presentation.ui.splash

import dk.nodes.template.presentation.util.SingleEvent

data class SplashViewState(
    val doneLoading: Boolean,
    val nstackUpdateAvailable: SingleEvent<Any>?
)