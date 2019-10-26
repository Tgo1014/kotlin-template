package dk.nodes.template.presentation.ui.base

import androidx.fragment.app.Fragment
import dk.nodes.template.presentation.util.ViewErrorController
import org.koin.android.ext.android.inject


abstract class BaseFragment : Fragment() {

    val defaultErrorController: ViewErrorController by inject()

}
