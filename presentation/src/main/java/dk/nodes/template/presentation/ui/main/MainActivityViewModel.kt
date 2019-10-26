package dk.nodes.template.presentation.ui.main

import androidx.lifecycle.viewModelScope
import dk.nodes.template.presentation.ui.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivityViewModel : BaseViewModel<MainActivityViewState>() {
    override val initState: MainActivityViewState = MainActivityViewState()

    fun example() = viewModelScope.launch {
        // Delay popup a bit so it's not super intrusive
        withContext(Dispatchers.IO) { delay(1000) }
    }
}