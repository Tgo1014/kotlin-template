package dk.eboks.app.presentation.ui.navigation.components

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dk.eboks.app.BuildConfig
import dk.eboks.app.R
import dk.eboks.app.domain.models.Translation
import dk.eboks.app.presentation.base.BaseFragment
import dk.eboks.app.presentation.ui.channels.screens.overview.ChannelOverviewActivity
import dk.eboks.app.presentation.ui.home.screens.HomeActivity
import dk.eboks.app.presentation.ui.mail.screens.overview.MailOverviewActivity
import dk.eboks.app.presentation.ui.notimplemented.screens.ComingSoonActivity
import dk.eboks.app.presentation.ui.senders.screens.overview.SendersOverviewActivity
import dk.eboks.app.presentation.ui.uploads.screens.UploadsActivity
import dk.eboks.app.util.Starter
import dk.eboks.app.util.disableShiftingMode
import kotlinx.android.synthetic.main.fragment_navbar_component.*
import javax.inject.Inject

/**
 * Created by bison on 09-02-2018.
 */
class NavBarComponentFragment : BaseFragment(), NavBarComponentContract.View {

    @Inject
    lateinit var presenter : NavBarComponentContract.Presenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_navbar_component, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        component.inject(this)
        presenter.onViewCreated(this, lifecycle)
        setupMainNavigation()
    }

    private fun setupMainNavigation()
    {
        mainNavigationBnv.inflateMenu(R.menu.main)
        var menu = mainNavigationBnv.menu
        menu.findItem(R.id.actionHome).title = Translation.mainnav.homeButton
        menu.findItem(R.id.actionMail).title = Translation.mainnav.mailButton
        menu.findItem(R.id.actionSenders).title = Translation.mainnav.sendersButton
        menu.findItem(R.id.actionChannels).title = Translation.mainnav.channelsButton
        menu.findItem(R.id.actionUploads).title = Translation.mainnav.uploadsButton
        mainNavigationBnv.disableShiftingMode()

        getBaseActivity()?.let {
            val menu_id = it.getNavigationMenuAction()
            if(menu_id != -1)
                currentMenuItem = menu_id
        }

        mainNavigationBnv.selectedItemId = currentMenuItem

        mainNavigationBnv.setOnNavigationItemSelectedListener { item ->
            var activityCls : Class<out Activity>? = null
            when(item.itemId)
            {
                R.id.actionHome -> {
                    activityCls = HomeActivity::class.java
                    currentMenuItem = R.id.actionHome
                }
                R.id.actionMail -> {
                    activityCls = MailOverviewActivity::class.java
                    currentMenuItem = R.id.actionMail
                }
                R.id.actionChannels -> {
                    activityCls = ChannelOverviewActivity::class.java
                    currentMenuItem = R.id.actionChannels
                }
                R.id.actionSenders -> {
                    if(BuildConfig.ENABLE_SENDERS) {
                        activityCls = SendersOverviewActivity::class.java
                    }
                    else
                    {
                        activityCls = ComingSoonActivity::class.java
                    }
                    currentMenuItem = R.id.actionSenders
                }
                R.id.actionUploads -> {
                    if(BuildConfig.ENABLE_UPLOADS)
                        activityCls = UploadsActivity::class.java
                    else
                        activityCls = ComingSoonActivity::class.java
                    currentMenuItem = R.id.actionUploads
                }
                else -> { }
            }
            activityCls?.let {
                startActivity(Intent(context, activityCls))
                //activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            }
            false
        }
    }

    companion object {
        var currentMenuItem = 0

        fun gotoChannels(activity: Activity)
        {
            currentMenuItem = R.id.actionChannels
            activity.Starter().activity(ChannelOverviewActivity::class.java).start()
        }

        fun gotoMail(activity: Activity)
        {
            currentMenuItem = R.id.actionMail
            activity.Starter().activity(MailOverviewActivity::class.java).start()
        }

        fun gotoInbox(activity: Activity)
        {
            currentMenuItem = R.id.actionMail
            activity.Starter().activity(MailOverviewActivity::class.java).putExtra("openInbox", true).start()
        }
    }

}