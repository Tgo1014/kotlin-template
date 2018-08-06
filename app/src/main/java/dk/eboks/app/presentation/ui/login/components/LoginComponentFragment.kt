package dk.eboks.app.presentation.ui.login.components

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.support.annotation.RequiresApi
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import dk.eboks.app.BuildConfig
import dk.eboks.app.R
import dk.eboks.app.domain.config.Config
import dk.eboks.app.domain.config.LoginProvider
import dk.eboks.app.domain.managers.EboksFormatter
import dk.eboks.app.domain.models.Translation
import dk.eboks.app.domain.models.local.ViewError
import dk.eboks.app.domain.models.login.User
import dk.eboks.app.domain.models.login.UserSettings
import dk.eboks.app.presentation.base.BaseFragment
import dk.eboks.app.presentation.ui.debug.components.DebugUsersComponentFragment
import dk.eboks.app.presentation.ui.dialogs.CustomFingerprintDialog
import dk.eboks.app.presentation.ui.start.screens.StartActivity
import dk.eboks.app.util.*
import dk.nodes.arch.domain.executor.SignalDispatcher.signal
import dk.nodes.locksmith.core.models.FingerprintDialogEvent.*
import kotlinx.android.synthetic.main.fragment_login_component.*
import timber.log.Timber
import javax.inject.Inject
import kotlinx.android.synthetic.main.include_toolbar.*

/**
 * Created by bison on 09-02-2018.
 */
class LoginComponentFragment : BaseFragment(), LoginComponentContract.View {

    private var emailCprIsValid = false
    private var passwordIsValid = false
    var handler = Handler()

    @Inject
    lateinit var presenter: LoginComponentContract.Presenter

    @Inject
    lateinit var formatter: EboksFormatter

    var showGreeting: Boolean = true
    var currentProvider: LoginProvider? = null
    var currentUser: User? = null
    var currentSettings: UserSettings? = null
    var verifyLoginProviderId : String? = null

    override fun onCreateView(
            inflater: LayoutInflater?,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater?.inflate(R.layout.fragment_login_component, container, false)
        return rootView
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        component.inject(this)
        presenter.onViewCreated(this, lifecycle)

        setupTopBar()

        arguments?.let { args ->
            showGreeting = args.getBoolean("showGreeting", true)
        }

        arguments?.getString("verifyLoginProviderId")?.let {
            verifyLoginProviderId = it
        }.guard { verifyLoginProviderId = null }

        continueBtn.setOnClickListener {
            onContinue()
        }
    }

    // shamelessly ripped from chnt
    private fun setupTopBar() {
        mainTb.setNavigationIcon(R.drawable.ic_red_close)
        mainTb.title = Translation.logoncredentials.title
        mainTb.setNavigationOnClickListener {
            hideKeyboard(view)
            activity.onBackPressed()
        }

        val menuRegist = mainTb.menu.add(Translation.logoncredentials.forgotPasswordButton)
        menuRegist.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM)
        menuRegist.setOnMenuItemClickListener { item: MenuItem ->
            hideKeyboard(this.view)
            getBaseActivity()?.openComponentDrawer(ForgotPasswordComponentFragment::class.java)
            true
        }
    }

    val keyboardListener = KeyboardUtils.SoftKeyboardToggleListener {
        if (it) {
            loginProvidersLl.visibility = View.GONE
            continueBtn.visibility = View.GONE
        } else {
            loginProvidersLl.visibility = View.VISIBLE
            continueBtn.visibility = View.VISIBLE
        }
    }

    override fun onResume() {
        super.onResume()
        Timber.d("onResume")

        presenter.setup(verifyLoginProviderId)
        setupCprEmailListeners()
        setupPasswordListener()
        KeyboardUtils.addKeyboardToggleListener(activity, keyboardListener)
    }

    override fun onPause() {
        Timber.d("onPause")
        handler.removeCallbacksAndMessages(null)
        KeyboardUtils.removeKeyboardToggleListener(keyboardListener)
        super.onPause()
    }


    private fun hideKeyboard(view: View?) {
        val inputMethodManager = getBaseActivity()?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    private fun onContinue() {
        Timber.i("onContinue with ${currentUser?.name}")
        currentUser?.let { user ->
            currentProvider?.let { provider ->
                val identity: String = if (provider.id == "email") {
                    user.emails[0].value ?: ""
                } else {
                    user.identity ?: ""
                }
                presenter.updateLoginState(
                        identity,
                        provider.id,
                        passwordEt.text.toString().trim(),
                        currentSettings?.activationCode
                )
                presenter.login()
                continuePb.visibility = View.VISIBLE
                continueBtn.isEnabled = false
            }
        }.guard {
            loginExistingUser()
        }
    }

    private fun loginExistingUser() {
        Timber.e("Log in to existing user")
        val emailOrCpr = cprEmailEt.text?.toString()?.trim() ?: ""
        val password = passwordEt.text?.toString()?.trim() ?: ""
        if (emailOrCpr.isNotBlank() && password.isNotBlank()) {
            val providerId = if (emailOrCpr.contains("@")) "email" else "cpr"
            presenter.updateLoginState(userName = emailOrCpr, providerId = providerId, password = password, activationCode = null)
            presenter.login()
        } else {
            Timber.e("Need a username and password to login to existing user")
        }
    }

    override fun proceedToApp() {
        //Timber.v("Signal - login_condition")
        signal("login_condition") // allow the eAuth2 authenticator to continue

        (activity as StartActivity).startMain()
    }


    override fun setupView(loginProvider: LoginProvider?, user: User?, settings: UserSettings, altLoginProviders: List<LoginProvider>) {
        Timber.i("SetupView called loginProvider: $loginProvider, user: $user, altProviders:  $altLoginProviders")
        continuePb.visibility = View.INVISIBLE

        loginProvider?.let { provider ->
            currentProvider = provider
            currentUser = user
            headerTv.visibility = View.GONE
            detailTv.visibility = View.GONE
            setupViewForProvider(user)
        }.guard {
            // no provider given setup for cpr/email (mobile access)
            headerTv.visibility = View.VISIBLE
            detailTv.visibility = View.VISIBLE
            cprEmailTil.visibility = View.VISIBLE
            passwordTil.visibility = View.VISIBLE
            continueBtn.visibility = View.VISIBLE
            userLl.visibility = View.GONE
        }

        setupAltLoginProviders(altLoginProviders)

        currentSettings = settings
        if (settings.hasFingerprint) {
            addFingerPrintProvider()
        }

        if (BuildConfig.DEBUG) {
            testUsersBtn.visibility = View.VISIBLE
            testUsersBtn.setOnClickListener {
                getBaseActivity()?.openComponentDrawer(DebugUsersComponentFragment::class.java)
            }
        }
    }

    override fun showActivationCodeDialog() {
        continuePb.visibility = View.INVISIBLE
        continueBtn.isEnabled = true

        getBaseActivity()?.openComponentDrawer(ActivationCodeComponentFragment::class.java)
    }

    override fun showError(viewError: ViewError) {
        continuePb.visibility = View.INVISIBLE
        continueBtn.isEnabled = true

        showErrorDialog(viewError)
    }

    override fun addFingerPrintProvider() {
        if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            val li = LayoutInflater.from(context)
            val v = li.inflate(R.layout.viewholder_login_provider, loginProvidersLl, false)
            v.findViewById<ImageView>(R.id.iconIv).setImageResource(R.drawable.ic_fingerprint)
            v.findViewById<TextView>(R.id.nameTv).text = Translation.logoncredentials.logonWithProvider.replace(
                    "[provider]",
                    Translation.profile.fingerprint
            )
            v.findViewById<TextView>(R.id.descTv).visibility = View.GONE

            v.setOnClickListener {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    showFingerprintDialog()
                }
            }

            loginProvidersLl.addView(v)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun showFingerprintDialog() {
        val customFingerprintDialog = CustomFingerprintDialog(context)

        customFingerprintDialog.setOnFingerprintDialogEventListener {
            customFingerprintDialog.dismiss()

            when (it) {
                CANCEL -> {
                    // Do nothing?
                }
                SUCCESS -> {
                    currentUser?.let { user ->
                        currentProvider?.let { provider ->
                            presenter.fingerPrintConfirmed(user)
//                            bgarof
//                            presenter.updateLoginState(user.name, provider.id, "todo", "todo")
//                            presenter.login()
                        }
                    }
                }
                ERROR_CIPHER,
                ERROR_ENROLLMENT,
                ERROR_HARDWARE,
                ERROR_SECURE,
                ERROR -> {
                    showErrorDialog(
                            ViewError(
                                    Translation.error.genericTitle,
                                    Translation.androidfingerprint.errorGeneric,
                                    true,
                                    false
                            )
                    )
                }
            }
        }

        customFingerprintDialog.onUsePasswordBtnListener = {
            // Todo add use password section
        }

        customFingerprintDialog.show()
    }

    private fun setupUserView(user: User?) {

        continueBtn.visibility = View.GONE
        passwordTil.visibility = View.VISIBLE
        if(BuildConfig.DEBUG)
        {
            showToast("Password preset to 'a12345' (DEBUG)")
            passwordEt.setText("a12345")
            continueBtn.isEnabled = true
        }

        // setting profile view
        userNameTv.text = user?.name
        userEmailCprTv.text = user?.emails?.firstOrNull()?.value

        val options = RequestOptions()
        options.error(R.drawable.ic_profile_placeholder)
        options.placeholder(R.drawable.ic_profile_placeholder)
        options.transforms(CenterCrop(), RoundedCorners(30))
        Glide.with(context)
                .load(user?.avatarUri)
                .apply(options)
                .into(userAvatarIv)

    }

    private fun setupViewForProvider(user: User?) {
        currentProvider?.let { provider ->
            when (provider.id) {
                "email" -> {
                    setupUserView(user)
                    cprEmailEt.inputType = InputType.TYPE_CLASS_TEXT and InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
                    cprEmailTil.visibility = View.GONE
                    userLl.visibility = View.VISIBLE
                }
                "cpr" -> {
                    user?.let { setupUserView(it) }
                    cprEmailEt.inputType = InputType.TYPE_CLASS_NUMBER
                    userLl.visibility = View.VISIBLE
                    cprEmailTil.visibility = View.VISIBLE
                    cprEmailTil.hint = Translation.logoncredentials.ssnHeader
                }
                else -> {
                    val providerFragment = provider.fragmentClass?.newInstance()
                    // if this is verification make sure the going back cancels the login instead presenting alternate providers
                    if(verifyLoginProviderId != null)
                    {
                        providerFragment?.putArg("closeLoginOnBack", true)
                    }
                    getBaseActivity()?.addFragmentOnTop(
                            R.id.containerFl, providerFragment
                    )
                }
            }
        }
    }

    private fun setupAltLoginProviders(providers: List<LoginProvider>) {

        loginProvidersLl.removeAllViews()
        loginProvidersLl.visibility = View.VISIBLE
        val li = LayoutInflater.from(context)

        for (provider in providers) {
            val v = li.inflate(R.layout.viewholder_login_provider, loginProvidersLl, false)

            // setting icon
            if (provider.icon != -1) {
                v.findViewById<ImageView>(R.id.iconIv).setImageResource(provider.icon)
            }

            //header
            v.findViewById<TextView>(R.id.nameTv).text = Translation.logoncredentials.logonWithProvider.replace(
                    "[provider]",
                    provider.name
            )

            //description
            provider.description?.let {
                v.findViewById<TextView>(R.id.descTv).text = it
            }.guard {
                v.findViewById<TextView>(R.id.descTv).visibility = View.GONE
            }
            v.setOnClickListener {
                presenter.switchLoginProvider(provider)
            }
            loginProvidersLl.addView(v)
        }
    }

    private fun setupPasswordListener() {
        passwordEt.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(password: Editable?) {
                setContinueButton()
                handler?.postDelayed({
                    setErrorMessages()
                }, 1200)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun setupCprEmailListeners() {
        cprEmailEt.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus -> }

        cprEmailEt.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(text: Editable?) {
                cprEmailTil.error = null
                setContinueButton()
                handler.postDelayed({
                    setErrorMessages()
                }, 1200)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun setErrorMessages() {
        if (emailCprIsValid) {
            cprEmailTil.error = null
        } else {
            cprEmailTil.error = Translation.logoncredentials.invalidCprorEmail
        }

        if (!passwordIsValid && !passwordEt.text.isNullOrBlank()) {
            passwordTil.error = Translation.logoncredentials.invalidPassword
        } else {
            passwordTil.error = null
        }
    }

    private fun setContinueButton() {

        emailCprIsValid = (cprEmailEt.text.isValidEmail() || cprEmailEt.text.isValidCpr())
        passwordIsValid = (!passwordEt.text.isNullOrBlank())

        currentProvider?.let { provider ->
            if (provider.id == "cpr") {
                val enabled = (emailCprIsValid && passwordIsValid)
                continueBtn.isEnabled = enabled
            }
            if (provider.id == "email") {
                continueBtn.isEnabled = passwordIsValid
            }
        }.guard {
            val enabled = (emailCprIsValid && passwordIsValid)
            continueBtn.isEnabled = enabled
        }

    }

//    private fun doUserLogin() {
//        currentUser?.let { user ->
//            currentProvider?.let { provider ->
//                presenter.updateLoginState(user, provider.id, "todo")
//                presenter.login() // TODO add credentials
//            }
//        }
//    }

    override fun showProgress(show: Boolean) {
        continueBtn.isEnabled = !show
        continuePb.visibility = if (show) View.VISIBLE else View.INVISIBLE
    }

    override fun onDestroy() {
        super.onDestroy()
        KeyboardUtils.removeAllKeyboardToggleListeners()
    }


}
