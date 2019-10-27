package dk.nodes.template.presentation.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dk.nodes.template.presentation.R
import dk.nodes.template.presentation.extensions.observeNonNull
import dk.nodes.template.presentation.ui.base.BaseFragment
import dk.nodes.template.presentation.ui.main.MainActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashFragment : BaseFragment() {

    private val splashViewModel: SplashViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        splashViewModel.viewState.observeNonNull(viewLifecycleOwner) { state ->
            handleNavigation(state)
        }
        splashViewModel.initAppState()
    }

    private fun handleNavigation(state: SplashViewState) {
        if (state.doneLoading) {
            showApp()
        }
    }

    private fun showApp() {
        MainActivity.start(requireContext())
        activity?.overridePendingTransition(0, 0)
    }
}