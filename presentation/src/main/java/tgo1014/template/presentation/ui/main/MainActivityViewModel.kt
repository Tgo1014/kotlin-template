package tgo1014.template.presentation.ui.main

import androidx.lifecycle.viewModelScope
import tgo1014.template.presentation.extensions.runOnIO
import tgo1014.template.presentation.ui.base.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivityViewModel : BaseViewModel<MainActivityViewState>() {

    override val initState: MainActivityViewState = MainActivityViewState()

    fun example() = viewModelScope.launch {
        // Delay popup a bit so it's not super intrusive
        runOnIO { delay(1000) }
    }
}