package dk.eboks.app.domain.senders.interactors.register

import dk.eboks.app.domain.models.local.ViewError
import dk.eboks.app.domain.models.sender.Registrations
import dk.nodes.arch.domain.interactor.Interactor

/**
 * Created by Christian on 3/28/2018.
 * @author Christian
 * @since 3/28/2018.
 */
interface GetRegistrationsInteractor : Interactor {
    var output: Output?

    interface Output {
        fun onRegistrationsLoaded(registrations: Registrations)
        fun onError(error: ViewError)
    }
}