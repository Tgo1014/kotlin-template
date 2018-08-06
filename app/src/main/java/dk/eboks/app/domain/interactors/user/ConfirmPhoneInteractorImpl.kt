package dk.eboks.app.domain.interactors.user

import dk.eboks.app.domain.repositories.UserRepository
import dk.eboks.app.network.Api
import dk.eboks.app.util.exceptionToViewError
import dk.nodes.arch.domain.executor.Executor
import dk.nodes.arch.domain.interactor.BaseInteractor

class ConfirmPhoneInteractorImpl(executor: Executor, val api: Api, val userRestRepo: UserRepository) : BaseInteractor(executor), ConfirmPhoneInteractor {
    override var output: ConfirmPhoneInteractor.Output? = null
    override var input: ConfirmPhoneInteractor.Input? = null

    override fun execute() {
        try {

            input?.let { args ->
                userRestRepo.confirmPhone(args.number, args.code)
            }
            runOnUIThread {
                output?.onConfirmPhone()
            }
        } catch (t: Throwable) {
            runOnUIThread {
                output?.onConfirmPhoneError(exceptionToViewError(t, shouldDisplay = false))
            }
        }

    }
}