package dk.eboks.app.domain.senders.interactors

import dk.eboks.app.domain.repositories.SenderCategoriesRepository
import dk.eboks.app.util.exceptionToViewError
import dk.nodes.arch.domain.executor.Executor
import dk.nodes.arch.domain.interactor.BaseInteractor
import javax.inject.Inject

/**
 * Created by chnt on 14/03/18.
 * @author chnt
 * @since 14/03/18.
 */
internal class GetSenderCategoriesInteractorImpl @Inject constructor(
    executor: Executor,
    private val senderCategoriesRepository: SenderCategoriesRepository
) : BaseInteractor(executor), GetSenderCategoriesInteractor {
    override var output: GetSenderCategoriesInteractor.Output? = null
    override var input: GetSenderCategoriesInteractor.Input? = null

    override fun execute() {
        try {
            val senders = senderCategoriesRepository.getSenderCategories(input?.cached ?: true)
            runOnUIThread {
                output?.onGetCategories(senders)
            }
        } catch (t: Throwable) {
            runOnUIThread {
                output?.onGetCategoriesError(exceptionToViewError(t))
            }
        }
    }
}