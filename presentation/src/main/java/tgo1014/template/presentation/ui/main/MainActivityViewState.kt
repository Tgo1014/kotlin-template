package tgo1014.template.presentation.ui.main

import tgo1014.template.presentation.util.SingleEvent

data class MainActivityViewState(
    val errorMessage: SingleEvent<String>? = null,
    val isLoading: Boolean = false
)