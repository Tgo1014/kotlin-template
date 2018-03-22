package dk.eboks.app.domain.interactors

import dk.eboks.app.domain.models.local.ViewError
import dk.eboks.app.domain.models.protocol.UserInfo
import dk.nodes.arch.domain.interactor.Interactor

/**
 * Created by bison on 24-06-2017.
 */
interface LoginInteractor : Interactor
{
    var input : Input?
    var output : Output?

    data class Input(val userInfo: UserInfo)

    interface Output {
        fun onLogin()
        fun onLoginError(error : ViewError)
    }
}