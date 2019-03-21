package dk.eboks.app.profile.presentation.ui.components.drawer

import dk.eboks.app.domain.interactors.encryption.EncryptUserLoginInfoInteractor
import dk.eboks.app.domain.managers.AppStateManager
import dk.eboks.app.domain.managers.UserSettingsManager
import dk.eboks.app.domain.models.Translation
import dk.eboks.app.domain.models.local.ViewError
import dk.eboks.app.domain.models.login.User
import dk.eboks.app.profile.interactors.SaveUserInteractor
import dk.nodes.arch.presentation.base.BasePresenterImpl
import timber.log.Timber
import javax.inject.Inject

internal class FingerHintComponentPresenter @Inject constructor(
    private val appState: AppStateManager,
    private val userSettingsManager: UserSettingsManager,
    private val encryptUserLoginInfoInteractor: EncryptUserLoginInfoInteractor,
    private val saveUserInteractor: SaveUserInteractor
) : FingerHintComponentContract.Presenter,
    BasePresenterImpl<FingerHintComponentContract.View>(),
    EncryptUserLoginInfoInteractor.Output,
    SaveUserInteractor.Output {

    override fun encryptUserLoginInfo() {
        val loginInfo = view?.getUserLoginInfo()

        Timber.d("encryptUserLoginInfo: $loginInfo")

        loginInfo?.let {
            it.actvationCode =
                userSettingsManager[appState.state?.currentUser?.id ?: 0].activationCode ?: ""
            encryptUserLoginInfoInteractor.output = this
            encryptUserLoginInfoInteractor.input = EncryptUserLoginInfoInteractor.Input(loginInfo)
            encryptUserLoginInfoInteractor.run()
        }
    }

    private fun saveFingerprintEnrollmentState() {
        Timber.d("saveFingerprintEnrollmentState")

        val currentUser = appState.state?.currentUser

        if (currentUser == null) {
            val viewError = ViewError(
                Translation.error.genericTitle,
                Translation.error.genericMessage,
                shouldDisplay = true,
                shouldCloseView = true
            )
            view?.showErrorDialog(viewError)
            return
        }

        userSettingsManager[currentUser.id].hasFingerprint = true
        userSettingsManager.save()

        saveUserInteractor.output = this
        saveUserInteractor.input = SaveUserInteractor.Input(currentUser)
        saveUserInteractor.run()
    }

    override fun onSuccess() {
        Timber.d("onLinkingSuccess")
        saveFingerprintEnrollmentState()
    }

    override fun onError(e: ViewError) {
        Timber.e("onError")
        view?.showErrorDialog(e)
    }

    override fun onSaveUser(user: User, numberOfUsers: Int) {
        Timber.d("onSaveUser")
        view?.finishView()
    }

    override fun onSaveUserError(error: ViewError) {
        Timber.d("onSaveUserError")
        view?.showErrorDialog(error)
    }
}