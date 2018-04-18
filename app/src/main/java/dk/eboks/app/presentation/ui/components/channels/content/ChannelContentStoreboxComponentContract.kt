package dk.eboks.app.presentation.ui.components.channels.content

import dk.eboks.app.presentation.base.BaseView
import dk.nodes.arch.presentation.base.BasePresenter

/**
 * Created by bison on 07-11-2017.
 */
interface ChannelContentStoreboxComponentContract {
    interface View : BaseView {}
    interface Presenter : BasePresenter<View> {}
}