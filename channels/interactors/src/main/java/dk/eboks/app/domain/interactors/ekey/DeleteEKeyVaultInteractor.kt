package dk.eboks.app.domain.interactors.ekey

import dk.eboks.app.domain.models.local.ViewError
import dk.nodes.arch.domain.interactor.Interactor

interface DeleteEKeyVaultInteractor : Interactor {
    interface Output {
        fun onDeleteEKeyVaultSuccess()
        fun onDeleteEKeyVaultError(viewError: ViewError)
    }

    data class Input(
        val signature: String,
        val signatureTime: String
    )

    var output: Output?
    var input: Input?
}