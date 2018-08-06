package dk.eboks.app.presentation.ui.channels.screens.overview

import dk.eboks.app.domain.managers.AppStateManager
import dk.nodes.arch.presentation.base.BasePresenterImpl

/**
 * Created by bison on 20-05-2017.
 */
class ChannelOverviewPresenter(val appStateManager: AppStateManager) :
        ChannelOverviewContract.Presenter,
        BasePresenterImpl<ChannelOverviewContract.View>()
{
    init {
    }

}