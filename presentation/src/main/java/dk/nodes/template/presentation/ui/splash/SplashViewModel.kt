package dk.nodes.template.presentation.ui.splash

import androidx.lifecycle.viewModelScope
import dk.nodes.template.presentation.ui.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class SplashViewModel@Inject constructor() : BaseViewModel<SplashViewState>() {

    override val initState: SplashViewState = SplashViewState(doneLoading = false, nstackUpdateAvailable = null)

    fun initAppState() = viewModelScope.launch {
        Timber.d("initAppState() - start")
        val deferredAppOpen = async(Dispatchers.IO) {  }
        // Other API calls that might be needed
        // ...
        // Splash should be shown for min. x milliseconds
        val deferredMinDelay = async(Dispatchers.IO) { delay(2000) }

        // Parallel execution, wait on both to finish
        val appOpenResult = deferredAppOpen.await()
        deferredMinDelay.await()
    }
}