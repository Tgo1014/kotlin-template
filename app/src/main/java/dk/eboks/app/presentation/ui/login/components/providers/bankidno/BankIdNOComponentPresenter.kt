package dk.eboks.app.presentation.ui.login.components.providers.bankidno

import dk.eboks.app.domain.config.AppConfig
import dk.eboks.app.keychain.interactors.authentication.MergeAndImpersonateInteractor
import dk.eboks.app.keychain.interactors.authentication.TransformTokenInteractor
import dk.eboks.app.keychain.interactors.authentication.VerifyProfileInteractor
import dk.eboks.app.domain.managers.AppStateManager
import dk.eboks.app.domain.managers.UserSettingsManager
import dk.eboks.app.presentation.ui.login.components.providers.WebLoginPresenter
import javax.inject.Inject

/**
 * Created by bison on 20-05-2017.
 */
class BankIdNOComponentPresenter @Inject constructor(
        appState: AppStateManager,
        transformTokenInteractor: TransformTokenInteractor,
        verifyProfileInteractor: VerifyProfileInteractor,
        mergeAndImpersonateInteractor: MergeAndImpersonateInteractor,
        userSettingsManager: UserSettingsManager,
        appConfig: AppConfig
) :
    WebLoginPresenter(
        appState,
        transformTokenInteractor,
        verifyProfileInteractor,
        mergeAndImpersonateInteractor,
        userSettingsManager,
        appConfig
    ) {

    override fun login(webToken: String) {
        appState.state?.loginState?.let {
            it.userLoginProviderId = "bankid_no"
        }
        super.login(webToken)
    }
}