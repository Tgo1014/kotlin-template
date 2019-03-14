package dk.eboks.app.presentation.ui.channels

import dagger.Binds
import dagger.Module
import dk.eboks.app.presentation.ui.channels.components.content.ekey.EkeyComponentContract
import dk.eboks.app.presentation.ui.channels.components.content.ekey.EkeyComponentPresenter
import dk.eboks.app.presentation.ui.channels.components.content.ekey.additem.EkeyAddItemComponentContract
import dk.eboks.app.presentation.ui.channels.components.content.ekey.additem.EkeyAddItemComponentPresenter
import dk.eboks.app.presentation.ui.channels.components.content.ekey.detail.EkeyDetailComponentContract
import dk.eboks.app.presentation.ui.channels.components.content.ekey.detail.EkeyDetailComponentPresenter
import dk.eboks.app.presentation.ui.channels.components.content.ekey.open.EkeyOpenItemComponentContract
import dk.eboks.app.presentation.ui.channels.components.content.ekey.open.EkeyOpenItemComponentPresenter
import dk.eboks.app.presentation.ui.channels.components.content.ekey.pin.EkeyPinComponentContract
import dk.eboks.app.presentation.ui.channels.components.content.ekey.pin.EkeyPinComponentPresenter
import dk.eboks.app.presentation.ui.channels.components.content.storebox.content.ChannelContentStoreboxComponentContract
import dk.eboks.app.presentation.ui.channels.components.content.storebox.content.ChannelContentStoreboxComponentPresenter
import dk.eboks.app.presentation.ui.channels.components.content.storebox.detail.ChannelContentStoreboxDetailComponentContract
import dk.eboks.app.presentation.ui.channels.components.content.storebox.detail.ChannelContentStoreboxDetailComponentPresenter
import dk.eboks.app.presentation.ui.channels.components.content.web.ChannelContentComponentContract
import dk.eboks.app.presentation.ui.channels.components.content.web.ChannelContentComponentPresenter
import dk.eboks.app.presentation.ui.channels.components.opening.ChannelOpeningComponentContract
import dk.eboks.app.presentation.ui.channels.components.opening.ChannelOpeningComponentPresenter
import dk.eboks.app.presentation.ui.channels.components.overview.ChannelOverviewComponentContract
import dk.eboks.app.presentation.ui.channels.components.overview.ChannelOverviewComponentPresenter
import dk.eboks.app.presentation.ui.channels.components.requirements.ChannelRequirementsComponentContract
import dk.eboks.app.presentation.ui.channels.components.requirements.ChannelRequirementsComponentPresenter
import dk.eboks.app.presentation.ui.channels.components.settings.ChannelSettingsComponentContract
import dk.eboks.app.presentation.ui.channels.components.settings.ChannelSettingsComponentPresenter
import dk.eboks.app.presentation.ui.channels.components.verification.ChannelVerificationComponentContract
import dk.eboks.app.presentation.ui.channels.components.verification.ChannelVerificationComponentPresenter
import dk.eboks.app.presentation.ui.channels.screens.content.ChannelContentContract
import dk.eboks.app.presentation.ui.channels.screens.content.ChannelContentPresenter
import dk.eboks.app.presentation.ui.channels.screens.content.ekey.EkeyContentContract
import dk.eboks.app.presentation.ui.channels.screens.content.ekey.EkeyContentPresenter
import dk.eboks.app.presentation.ui.channels.screens.content.storebox.ConnectStoreboxContract
import dk.eboks.app.presentation.ui.channels.screens.content.storebox.ConnectStoreboxPresenter
import dk.eboks.app.presentation.ui.channels.screens.overview.ChannelOverviewContract
import dk.eboks.app.presentation.ui.channels.screens.overview.ChannelOverviewPresenter
import dk.nodes.arch.domain.injection.scopes.ActivityScope

@Module
abstract class ChannelsBindingPresentationModule {
    @ActivityScope
    @Binds
    internal abstract fun bindChannelsPresenter(presenter: ChannelOverviewPresenter): ChannelOverviewContract.Presenter

    @ActivityScope
    @Binds
    internal abstract fun bindChannelListComponentPresenter(presenter: ChannelOverviewComponentPresenter): ChannelOverviewComponentContract.Presenter

    @ActivityScope
    @Binds
    internal abstract fun bindChannelSettingsPopUpComponentPresenter(presenter: ChannelRequirementsComponentPresenter): ChannelRequirementsComponentContract.Presenter

    @ActivityScope
    @Binds
    internal abstract fun bindChannelOpeningComponentPresenter(presenter: ChannelOpeningComponentPresenter): ChannelOpeningComponentContract.Presenter

    @ActivityScope
    @Binds
    internal abstract fun bindChannelVerificationComponentPresenter(presenter: ChannelVerificationComponentPresenter): ChannelVerificationComponentContract.Presenter

    @ActivityScope
    @Binds
    internal abstract fun bindChannelContentComponentPresenter(presenter: ChannelContentComponentPresenter): ChannelContentComponentContract.Presenter

    @ActivityScope
    @Binds
    internal abstract fun bindChannelContentStoreboxComponentPresenter(presenter: ChannelContentStoreboxComponentPresenter): ChannelContentStoreboxComponentContract.Presenter

    @ActivityScope
    @Binds
    internal abstract fun bindChannelContentStoreboxDetailComponentPresenter(presenter: ChannelContentStoreboxDetailComponentPresenter): ChannelContentStoreboxDetailComponentContract.Presenter

    @ActivityScope
    @Binds
    internal abstract fun bindChannelSettingsComponentPresenter(presenter: ChannelSettingsComponentPresenter): ChannelSettingsComponentContract.Presenter

    @ActivityScope
    @Binds
    internal abstract fun bindChannelContentPresenter(presenter: ChannelContentPresenter): ChannelContentContract.Presenter

    @ActivityScope
    @Binds
    internal abstract fun bindEkeyContentPresenter(presenter: EkeyContentPresenter): EkeyContentContract.Presenter

    @ActivityScope
    @Binds
    internal abstract fun bindEkeyComponentPresenter(presenter: EkeyComponentPresenter): EkeyComponentContract.Presenter

    @ActivityScope
    @Binds
    internal abstract fun bindEkeyAddItemComponentPresenter(presenter: EkeyAddItemComponentPresenter): EkeyAddItemComponentContract.Presenter

    @ActivityScope
    @Binds
    internal abstract fun bindEkeyDetailComponentPresenter(presenter: EkeyDetailComponentPresenter): EkeyDetailComponentContract.Presenter

    @ActivityScope
    @Binds
    internal abstract fun bindEkeyOpenItemComponentPresenter(presenter: EkeyOpenItemComponentPresenter): EkeyOpenItemComponentContract.Presenter

    @ActivityScope
    @Binds
    internal abstract fun bindEkeyPinComponentPresenter(presenter: EkeyPinComponentPresenter): EkeyPinComponentContract.Presenter

    @ActivityScope
    @Binds
    internal abstract fun bindConnectStoreboxPresenter(presenter: ConnectStoreboxPresenter): ConnectStoreboxContract.Presenter
}