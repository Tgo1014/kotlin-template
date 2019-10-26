package dk.nodes.template.presentation.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.DaggerAppCompatActivity
import dk.nodes.template.presentation.extensions.getViewModel
import dk.nodes.template.presentation.extensions.lifecycleAwareLazy
import javax.inject.Inject

abstract class BaseActivity : DaggerAppCompatActivity() {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    protected inline fun <reified VM : ViewModel> getViewModel(): VM =
        getViewModel(viewModelFactory)

    protected inline fun <reified VM : ViewModel> viewModel(): Lazy<VM> {
        return lifecycleAwareLazy(this) { getViewModel<VM>() }
    }
}