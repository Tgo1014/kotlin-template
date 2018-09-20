package dk.eboks.app.domain.interactors.ekey

import dk.eboks.app.domain.exceptions.InteractorException
import dk.eboks.app.network.Api
import dk.eboks.app.util.exceptionToViewError
import dk.eboks.app.util.guard
import dk.nodes.arch.domain.executor.Executor
import dk.nodes.arch.domain.interactor.BaseInteractor

class GetEKeyVaultInteractorImpl(executor: Executor, private val api: Api) :
        BaseInteractor(executor),
        GetEKeyVaultInteractor {

    override var output: GetEKeyVaultInteractor.Output? = null
    override var input: GetEKeyVaultInteractor.Input? = null

    override fun execute() {
        try {
            input?.let {
                val response = api.keyVaultGet(it.signatureTime, it.signature).execute()

                if (response?.isSuccessful == true) {
                    output?.onGetEKeyVaultSuccess()
                }
            }.guard {
                throw InteractorException("Wrong input")
            }

        } catch (exception: Exception) {
            showViewException(exception)
            return
        }
    }

    private fun showViewException(exception: Exception) {
        val viewError = exceptionToViewError(exception)
        output?.onGetEKeyVaultError(viewError)
    }
}