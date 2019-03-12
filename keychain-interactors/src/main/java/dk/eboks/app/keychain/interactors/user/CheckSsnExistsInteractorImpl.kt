package dk.eboks.app.keychain.interactors.user

import dk.eboks.app.domain.exceptions.InteractorException
import dk.eboks.app.domain.repositories.UserRepository
import dk.eboks.app.util.exceptionToViewError
import dk.eboks.app.util.guard
import dk.nodes.arch.domain.executor.Executor
import dk.nodes.arch.domain.interactor.BaseInteractor
import javax.inject.Inject

internal class CheckSsnExistsInteractorImpl @Inject constructor(
    executor: Executor,
    private val userRestRepo: UserRepository
) : BaseInteractor(executor), CheckSsnExistsInteractor {
    override var output: CheckSsnExistsInteractor.Output? = null
    override var input: CheckSsnExistsInteractor.Input? = null

    override fun execute() {
        var exists = true
        try {
            input?.ssn?.let {
                exists = userRestRepo.checkSsn(it)
            }.guard { InteractorException("no args") }
            runOnUIThread {
                output?.onCheckSsnExists(exists)
            }
        } catch (t: Throwable) {
            runOnUIThread {
                output?.onCheckSsnExistsError(exceptionToViewError(t, shouldDisplay = false))
            }
        }
    }
}