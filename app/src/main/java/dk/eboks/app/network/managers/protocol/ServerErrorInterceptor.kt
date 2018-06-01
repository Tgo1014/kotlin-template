package dk.eboks.app.network.managers.protocol

import com.google.gson.Gson
import dk.eboks.app.App
import dk.eboks.app.domain.exceptions.ServerErrorException
import dk.eboks.app.domain.models.protocol.ServerError
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by bison on 01/02/18.
 */
class ServerErrorInterceptor : okhttp3.Interceptor  {
    @Inject lateinit var gson : Gson

    @Throws(java.io.IOException::class, ServerErrorException::class)
    override fun intercept(chain: okhttp3.Interceptor.Chain): okhttp3.Response {
        App.instance().appComponent.inject(this)

        val response = chain.proceed(chain.request())
        if(!response.isSuccessful)
        {
            response.peekBody(16384L)?.string()?.let { buffer ->
                try {
                    val se = gson.fromJson<ServerError>(buffer, ServerError::class.java) ?: return response
                    if(se.id == null)
                        return response
                    throw(ServerErrorException(se))
                }
                catch (t : Throwable)
                {
                    if(t is ServerErrorException || t.cause is ServerErrorException)
                    {
                        Timber.e("Rethrowing ServerErrorException")
                        throw(t)
                    }
                    else
                    {
                        Timber.e("Could not parse a ServerError passing through body")
                        Timber.e(t)
                        t.printStackTrace()
                        return response
                    }
                }
            }
        }
        return response
    }
}