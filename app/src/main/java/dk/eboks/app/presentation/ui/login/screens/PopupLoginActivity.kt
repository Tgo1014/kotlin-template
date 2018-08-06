package dk.eboks.app.presentation.ui.login.screens

import android.app.Activity
import android.os.Bundle
import dk.eboks.app.R
import dk.eboks.app.presentation.base.BaseActivity
import dk.eboks.app.presentation.ui.login.components.LoginComponentFragment
import dk.eboks.app.util.guard
import dk.eboks.app.util.putArg
import javax.inject.Inject

class PopupLoginActivity : BaseActivity(), PopupLoginContract.View {
    @Inject lateinit var presenter: PopupLoginContract.Presenter

    companion object {
        val REQUEST_VERIFICATION : Int = 13445
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_popup_login)

        // set result cancelled by default so we only have to change it on positive outcomes in the views
        setResult(Activity.RESULT_CANCELED)
        component.inject(this)
        presenter.onViewCreated(this, lifecycle)
        supportFragmentManager.addOnBackStackChangedListener {
            if (supportFragmentManager.backStackEntryCount == 0) {
                if (!isDestroyed)
                    finish()
            }
        }
        intent?.getStringExtra("verifyLoginProviderId")?.let { provider_id->
            setRootFragment(R.id.containerFl, LoginComponentFragment().putArg("verifyLoginProviderId", provider_id))
        }.guard {
            setRootFragment(R.id.containerFl, LoginComponentFragment())
        }
    }

}