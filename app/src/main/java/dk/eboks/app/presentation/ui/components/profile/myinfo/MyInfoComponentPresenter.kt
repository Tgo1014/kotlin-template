package dk.eboks.app.presentation.ui.components.profile.myinfo

import dk.eboks.app.domain.interactors.user.SaveUserInteractor
import dk.eboks.app.domain.interactors.user.UpdateUserInteractor
import dk.eboks.app.domain.managers.AppStateManager
import dk.eboks.app.domain.models.Translation
import dk.eboks.app.domain.models.local.ViewError
import dk.eboks.app.domain.models.login.User
import dk.nodes.arch.presentation.base.BasePresenterImpl
import timber.log.Timber
import javax.inject.Inject

class MyInfoComponentPresenter @Inject constructor(
        val appState: AppStateManager,
        val saveUserInteractor: SaveUserInteractor,
        val updateUserInteractor: UpdateUserInteractor
) :
        MyInfoComponentContract.Presenter,
        BasePresenterImpl<MyInfoComponentContract.View>(),
        UpdateUserInteractor.Output,
        SaveUserInteractor.Output {
    init {
        saveUserInteractor.output = this
        updateUserInteractor.output = this
    }

    override fun setup() {
        appState.state?.currentUser?.let { user ->
            runAction { v ->
                v.setName(user.name)
                user.getPrimaryEmail()?.let { v.setPrimaryEmail(it) }
                user.getSecondaryEmail()?.let { v.setSecondaryEmail(it) }
                user.mobileNumber?.value?.let { v.setMobileNumber(it) }
                v.setNewsletter(user.newsletter)
            }
        }
    }

    override fun save() {
        runAction { v ->
            appState.state?.currentUser?.let { user ->
                user.setPrimaryEmail(v.getPrimaryEmail())
                user.setSecondaryEmail(v.getSecondaryEmail())

                user.name = v.getName()
                user.mobileNumber?.value = v.getMobileNumber()
                user.newsletter = v.getNewsletter()
                v.showProgress(true)
                // save user locally
                saveUserInteractor.input = SaveUserInteractor.Input(user)
                saveUserInteractor.run()


                v.onDone()
            }

            //todo putting in mock data as the app.state does not save the profile atm
            // save user on the server
            var user = User(555,"updatetest")
            updateUserInteractor.input = UpdateUserInteractor.Input(user)
            updateUserInteractor.run()

        }
    }

    override fun onSaveUser(user: User, numberOfUsers: Int) {
        Timber.e("User saved")
        runAction { v ->
            v.setSaveEnabled(false)
            v.showProgress(false)
            v.showToast(Translation.profile.yourInfoWasSaved)
        }
    }

    override fun onSaveUserError(error: ViewError) {
        runAction { v ->
            v.showProgress(false)
            v.showErrorDialog(error)
        }
    }

    override fun onUpdateProfile() {
        //todo
        Timber.e("onUpdateProfile succesfull")
    }

    override fun onUpdateProfileError(error: ViewError) {
        //todo
        Timber.e(error.message)
    }
}