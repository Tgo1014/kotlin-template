package dk.eboks.app.domain.interactors.authentication

import dk.eboks.app.domain.managers.*
import dk.eboks.app.domain.models.Translation
import dk.eboks.app.domain.models.local.ViewError
import dk.eboks.app.domain.repositories.MailCategoriesRepository
import dk.eboks.app.network.Api
import dk.eboks.app.util.exceptionToViewError
import dk.eboks.app.util.guard
import dk.nodes.arch.domain.executor.Executor
import dk.nodes.arch.domain.interactor.BaseInteractor
import timber.log.Timber


/**
 * Created by Christian on 5/28/2018.
 * @author   Christian
 * @since    5/28/2018.
 */
class VerifyProfileInteractorImpl(
        executor: Executor, val api: Api,
        val appStateManager: AppStateManager,
        val userManager: UserManager,
        val userSettingsManager: UserSettingsManager,
        val authClient: AuthClient,
        val cacheManager: CacheManager,
        val foldersRepositoryMail: MailCategoriesRepository
) : BaseInteractor(executor), VerifyProfileInteractor {
    override var output: VerifyProfileInteractor.Output? = null
    override var input: VerifyProfileInteractor.Input? = null

    override fun execute() {
        try {
            input?.verificationState?.let { verificationState ->
                authClient.transformKspToken(verificationState.kspToken, verificationState.oldAccessToken, longClient = appStateManager.state?.currentSettings?.stayLoggedIn ?: false)?.let { token ->
                    appStateManager.state?.loginState?.token = token

                    val jwtJson = authClient.decodeJWTBody(token.access_token)

                    // if this verification during signup, we don't need to check for migration since the account isn't
                    // yet created. Hence there is no source account to migrate from yo
                    var newIdentity : String? = null
                    if(verificationState.signupVerification)
                    {
                        Timber.e("Doing signup verification for the lord")
                        if(jwtJson.has("sub"))
                        {
                            val arr = jwtJson.getString("sub").split("-").toTypedArray()
                            newIdentity = arr[1]
                            Timber.e("Got identity in JWT = $newIdentity")
                        }
                    }

                    // do we need to do the whole migration dance?
                    if(jwtJson.has("allow-migrate-user") && !verificationState.signupVerification)
                    {
                        verificationState.allowMigrateUserId = jwtJson.getString("allow-migrate-user")
                        Timber.e("Token has allow-migrate-user id = ${verificationState.allowMigrateUserId}")
                        runOnUIThread {
                            output?.onAlreadyVerifiedProfile()
                        }
                    }
                    else // narp
                    {
                        // if this is during signup there is no user before create user is called, only a ksp ticket, hence get profile will fail
                        if(!verificationState.signupVerification) {
                            val userResult = api.getUserProfile().execute()
                            userResult?.body()?.let { user ->
                                // update the states
                                Timber.e("Saving user $user")
                                val newUser = userManager.put(user)
                                val newSettings = userSettingsManager.get(newUser.id)

                                appStateManager.state?.loginState?.userLoginProviderId?.let {
                                    newSettings.lastLoginProviderId = "cpr"
                                }

                                cacheManager.clearStores()

                                userSettingsManager.put(newSettings)
                                appStateManager.state?.loginState?.lastUser = newUser
                                appStateManager.state?.currentUser = newUser
                                appStateManager.state?.currentSettings = newSettings
                                appStateManager.state?.verificationState = null // verification done
                            }
                            appStateManager.save()
                        }
                        runOnUIThread {
                            output?.onVerificationSuccess(newIdentity)
                        }
                        appStateManager.state?.selectedFolders = foldersRepositoryMail.getMailCategories(false)
                    }
                }.guard {
                    runOnUIThread {
                        output?.onVerificationError(ViewError(title = Translation.error.genericTitle, message = Translation.error.genericMessage, shouldCloseView = true)) // TODO better error
                    }
                }
            }
        } catch (t: Throwable) {
            runOnUIThread {
                output?.onVerificationError(exceptionToViewError(t))
            }
        }
    }

}