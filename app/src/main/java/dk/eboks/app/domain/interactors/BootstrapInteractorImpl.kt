package dk.eboks.app.domain.interactors

import dk.eboks.app.domain.config.Config
import dk.eboks.app.domain.managers.*
import dk.eboks.app.domain.models.Translation
import dk.eboks.app.domain.models.local.ViewError
import dk.eboks.app.domain.repositories.SettingsRepository
import dk.eboks.app.network.Api
import dk.nodes.arch.domain.executor.Executor
import dk.nodes.arch.domain.interactor.BaseInteractor
import timber.log.Timber

/**
 * Created by bison on 24-06-2017.
 */
class BootstrapInteractorImpl(executor: Executor, val guidManager: GuidManager,
                              val settingsRepository: SettingsRepository,
                              val appStateManager: AppStateManager,
                              val fileCacheManager: FileCacheManager,
                              val cacheManager: CacheManager,
                              val userManager: UserManager,
                              val api: Api) : BaseInteractor(executor), BootstrapInteractor {
    override var output: BootstrapInteractor.Output? = null
    override var input: BootstrapInteractor.Input? = null

    override fun execute() {

        try {
            // we don't use input in this example but we could:
            input?.let {
                // do something with unwrapped input

            }

            val result = api.getResourceLinks().execute()
            if(result.isSuccessful)
            {
                result?.body()?.let { links->
                    Config.resourceLinks = links
                }
            }


            val hasUsers = userManager.users.isNotEmpty()

            val loginState = appStateManager.state?.loginState
            loginState?.firstLogin = !hasUsers

            val settings = settingsRepository.get()
            if (settings.deviceId.isBlank()) {
                settings.deviceId = guidManager.generateGuid()
                Timber.d("No device ID found, generating new id: ${settings.deviceId}")
            }
            settingsRepository.put(settings)

            loginState?.kspToken = ""
            loginState?.token = null
            loginState?.activationCode = null
            loginState?.userName = null
            loginState?.userPassWord = null

            // clear memory caches, this is necessary when the app hasn't been force closed in case another user
            // is logged in
            Timber.d("Clearing CacheStore memory")
            cacheManager.clearStoresMemoryOnly()
            fileCacheManager.clearMemoryOnly()

            Timber.d("LoginState: $loginState?")
            //Thread.sleep(2000)
            runOnUIThread {
                output?.onBootstrapDone(hasUsers)
            }
        } catch (e: Exception) {
            runOnUIThread {
                output?.onBootstrapError(ViewError(title = Translation.error.startupTitle, message = Translation.error.startupMessage, shouldCloseView = true))
            }
        }
    }
}