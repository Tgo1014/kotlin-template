package dk.nodes.template.presentation.ui.splash

import androidx.lifecycle.viewModelScope
import dk.nodes.template.presentation.ui.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import timber.log.Timber

class SplashViewModel : BaseViewModel<SplashViewState>() {

    override val initState: SplashViewState = SplashViewState(doneLoading = false)

    fun initAppState() = viewModelScope.launch {
        Timber.d("initAppState() - start")
        val deferredAppOpen = async(Dispatchers.IO) { }
        // Other API calls that might be needed
        // ...
        // Splash should be shown for min. x milliseconds
        val deferredMinDelay = async(Dispatchers.IO) { /*delay(2000)*/ }

        // Parallel execution, wait on both to finish
        val appOpenResult = deferredAppOpen.await()
        deferredMinDelay.await()

        Timber.d("initAppState() - end")
        state = when (appOpenResult) {
//            is AppOpenResult.Success -> {
//                nStackPresenter.saveAppState(appOpenResult.appUpdateResponse.data)
//                state.copy(doneLoading = true, nstackUpdateAvailable = SingleEvent(appOpenResult.appUpdateResponse.data))
//            }
            else -> state.copy(doneLoading = true)
        }
    }
}