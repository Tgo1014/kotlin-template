package dk.eboks.app.profile.presentation.ui.components.main

import androidx.lifecycle.Lifecycle
import dk.eboks.app.domain.managers.AppStateManager
import dk.eboks.app.domain.models.local.ViewError
import dk.eboks.app.domain.models.login.User
import dk.eboks.app.profile.interactors.GetUserProfileInteractor
import dk.eboks.app.profile.interactors.SaveUserInteractor
import dk.eboks.app.profile.interactors.SaveUserSettingsInteractor
import dk.nodes.arch.presentation.base.BasePresenterImpl
import timber.log.Timber
import javax.inject.Inject

internal class ProfileInfoComponentPresenter @Inject constructor(
    private val appState: AppStateManager,
    private val saveUserInteractor: SaveUserInteractor,
    private val saveUserSettingsInteractor: SaveUserSettingsInteractor,
    private val getUserProfileInteractor: GetUserProfileInteractor
) :
    ProfileInfoComponentContract.Presenter,
    BasePresenterImpl<ProfileInfoComponentContract.View>(),
    SaveUserInteractor.Output,
    GetUserProfileInteractor.Output {

    override var isUserVerified: Boolean = false

    override fun onViewCreated(view: ProfileInfoComponentContract.View, lifecycle: Lifecycle) {
        super.onViewCreated(view, lifecycle)
        saveUserInteractor.output = this
        getUserProfileInteractor.output = this
        isUserVerified = appState.state?.currentUser?.verified ?: false
        view.setName(appState.state?.currentUser?.name ?: "")
    }

    override fun loadUserData(showProgress: Boolean) {
        Timber.d("loadUserData")

        val currentUser = appState.state?.currentUser

        if (currentUser == null) {
            // TODO add some error handling
            Timber.e("Null Current User")
//            return
        }

        view { showProgress(showProgress) }

        getUserProfileInteractor.run()
    }

    override fun onGetUser(user: User) {
        view {
            detachListeners()
            setName(user.name)
            setVerified(user.verified)
            showFingerprintOptionIfSupported()

            setProfileImage(user.avatarUri)

            appState.state?.currentSettings?.let {
                showFingerprintEnabled(it.hasFingerprint, it.lastLoginProviderId)
                showKeepMeSignedIn(it.stayLoggedIn)
            }
            showProgress(false)
            attachListeners()
        }
    }

    override fun onGetUserError(error: ViewError) {
        view {
            showProgress(false)
            showErrorDialog(error)
        }
    }

    override fun saveUserImg(uri: String) {
        appState.state?.currentUser?.avatarUri = uri
        appState.save()

        appState.state?.currentSettings?.let {
            saveUserSettingsInteractor.input = SaveUserSettingsInteractor.Input(it)
            saveUserSettingsInteractor.run()
        }
    }

    override fun enableUserFingerprint(enable: Boolean) {
        appState.state?.currentSettings?.hasFingerprint = enable
        appState.save()

        appState.state?.currentSettings?.let {
            saveUserSettingsInteractor.input = SaveUserSettingsInteractor.Input(it)
            saveUserSettingsInteractor.run()
        }
    }

    override fun enableKeepMeSignedIn(enable: Boolean) {
        appState.state?.currentSettings?.stayLoggedIn = enable
        appState.save()

        appState.state?.currentSettings?.let {
            saveUserSettingsInteractor.input = SaveUserSettingsInteractor.Input(it)
            saveUserSettingsInteractor.run()
        }
    }

    override fun onSaveUser(user: User, numberOfUsers: Int) {
        loadUserData(false)
    }

    override fun onSaveUserError(error: ViewError) {
        view {
            showProgress(false)
            showErrorDialog(error)
        }
    }

    override fun doLogout() {
        appState.state?.currentSettings = null
        appState.state?.loginState?.userPassWord = ""
        appState.state?.loginState?.userName = ""
        appState.state?.loginState?.token = null
        appState.state?.openingState?.acceptPrivateTerms = false
        // activationCode still used ?
        appState.state?.loginState?.activationCode = null
        appState.save()
        view { logout() }
    }
}