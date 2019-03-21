package dk.eboks.app.network.managers

import android.util.Base64
import com.google.gson.Gson
import dk.eboks.app.domain.config.AppConfig
import dk.eboks.app.domain.exceptions.InteractorException
import dk.eboks.app.domain.managers.AppStateManager
import dk.eboks.app.domain.managers.AuthClient
import dk.eboks.app.domain.managers.AuthException
import dk.eboks.app.domain.managers.CryptoManager
import dk.eboks.app.domain.models.login.AccessToken
import dk.eboks.app.domain.repositories.SettingsRepository
import dk.eboks.app.network.BuildConfig
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import timber.log.Timber
import java.io.UnsupportedEncodingException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class AuthClientImpl @Inject constructor(
    private val cryptoManager: CryptoManager,
    private val settingsRepository: SettingsRepository,
    private val appStateManager: AppStateManager,
    private val appConfig: AppConfig
) : AuthClient {
    private var httpClient: OkHttpClient
    private var gson: Gson = Gson()

    init {
        val clientBuilder = OkHttpClient.Builder()
            .connectTimeout(45, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor())

        if (BuildConfig.DEBUG) {
            val logging = okhttp3.logging.HttpLoggingInterceptor()
            logging.level = okhttp3.logging.HttpLoggingInterceptor.Level.BODY
            clientBuilder.addInterceptor(logging)
        }

        httpClient = clientBuilder.build()
    }

    override fun transformKspToken(
        kspToken: String,
        oauthToken: String?,
        longClient: Boolean
    ): AccessToken? {
        appStateManager.state?.loginState?.useCustomClientId = true
        appStateManager.state?.loginState?.useLongClientId = longClient
        appStateManager.save()
        val keys = getKeys(longClient)

        val formBody = FormBody.Builder()
            .add("kspwebtoken", kspToken)
            .add("grant_type", "kspwebtoken")
            .add("scope", "mobileapi offline_access")
            .add("client_id", keys.first)
            .add("client_secret", keys.second)

        if (oauthToken != null)
            formBody.add("oauthtoken", oauthToken)

        val request = Request.Builder()
            .url(appConfig.getAuthUrl())
            .post(formBody.build())
            .build()

        val result = httpClient.newCall(request).execute()
        if (result.isSuccessful) {
            result.body()?.string()?.let { json ->
                gson.fromJson(json, AccessToken::class.java)?.let { token ->
                    return token
                }
            }
        }
        return null
    }

    override fun impersonate(token: String, userId: String): AccessToken? {
        appStateManager.state?.loginState?.useCustomClientId = true
        appStateManager.state?.loginState?.useLongClientId = false
        appStateManager.save()
        val keys = getKeys(false)

        val formBody = FormBody.Builder()
            .add("token", token)
            .add("userid", userId)
            .add("grant_type", "impersonate")
            .add("scope", "mobileapi offline_access")
            .add("client_id", keys.first)
            .add("client_secret", keys.second)
            .build()

        val request = Request.Builder()
            .url(appConfig.getAuthUrl())
            .post(formBody)
            .build()

        val result = httpClient.newCall(request).execute()
        if (result.isSuccessful) {
            result.body()?.string()?.let { json ->
                gson.fromJson(json, AccessToken::class.java)?.let { token ->
                    return token
                }
            }
        }
        throw(InteractorException("impersonate failed"))
    }

    override fun transformRefreshToken(refreshToken: String, longClient: Boolean): AccessToken? {
        // CustomID/non-customID would be determined at the original login, and the same should be used here
        val keys = getKeys(longClient)

        val formBody = FormBody.Builder()
            .add("refresh_token", refreshToken)
            .add("grant_type", "refresh_token")
            .add("scope", "mobileapi offline_access")
            .add("client_id", keys.first)
            .add("client_secret", keys.second)
            .build()

        val request = Request.Builder()
            .url(appConfig.getAuthUrl())
            .post(formBody)
            .build()

        val result = httpClient.newCall(request).execute()
        if (result.isSuccessful) {
            result.body()?.string()?.let { json ->
                gson.fromJson(json, AccessToken::class.java)?.let { token ->
                    return token
                }
            }
        }
        return null
    }

    // Throws AuthException with http error code on other values than 200 okay
    override fun login(
        username: String,
        password: String,
        longClient: Boolean,
        bearerToken: String?,
        verifyOnly: Boolean,
        userid: String?
    ): AccessToken? {
        appStateManager.state?.loginState?.useCustomClientId = false
        appStateManager.state?.loginState?.useLongClientId = longClient
        appStateManager.save()
        val keys = getKeys(longClient)

        val formBody = FormBody.Builder()
            .add("grant_type", "password")
            .add("client_id", keys.first)
            .add("client_secret", keys.second)
            .add("username", username)
            .add("password", password)

        if (verifyOnly)
            formBody.add("scope", "mobileapi")
        else
            formBody.add("scope", "mobileapi offline_access")

//      ---------- mobile access ----------
        val challengeFormatter = SimpleDateFormat("yyyyMMddHHmmss", Locale.UK)
        challengeFormatter.timeZone = TimeZone.getTimeZone("UTC")

        val localFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZZZ", Locale.UK)

        val baseTime = Date()
        val challengeTime = challengeFormatter.format(baseTime)
        val localTime = localFormatter.format(baseTime)

        val deviceId = settingsRepository.get().deviceId

        Timber.v("login - ChallengeTime: $challengeTime, LocalTime: $localTime")

        userid?.let { id ->
            if (!cryptoManager.hasActivation(id)) {
                formBody.add("acr_values", "deviceid:$deviceId")
            } else {
                val challenge = "EBOKS:$username:$password:$deviceId:$challengeTime"
                Timber.i("login - challenge: $challenge")

                cryptoManager.getActivation(id)?.privateKey?.let { privateKey ->
                    val hashedChallenge = cryptoManager.hashStringData(challenge, privateKey)
                    Timber.i("login - hashedchallenge: $hashedChallenge")
                    formBody.add(
                        "acr_values",
                        "challenge:$hashedChallenge timestamp:$localTime deviceid:$deviceId"
                    )
                }
            }
        }
//      -----------------------------------
        val requestBuilder = Request.Builder()
            .url(appConfig.getAuthUrl())
            .post(formBody.build())

        bearerToken?.let { token ->
            requestBuilder.addHeader("Authorization", "Bearer $token")
        }

        val request = requestBuilder.build()

        val result = httpClient.newCall(request).execute()

        if (result.isSuccessful) {
            // do not read the token if we're only verifying the login
            if (!verifyOnly) {
                result.body()?.string()?.let { json ->
                    gson.fromJson(json, AccessToken::class.java)?.let { token ->
                        return token
                    }
                }
            }
        } else {
            result.body()?.string()?.let { json ->
                var jsonObj: JSONObject? = null
                try {
                    jsonObj = JSONObject(json)
                } catch (t: Throwable) {
                    Timber.e(t)
                    throw(AuthException(result.code(), ""))
                }

                // Timber.e("Parsed json obj ${jsonObj?.toString(4)} errorDescription = ${jsonObj?.getString("error_description")}")

                throw(AuthException(
                    result.code(), jsonObj.getString("error_description")
                        ?: ""
                ))
            }
        }
        return null
    }

    private fun getKeys(isLong: Boolean): Pair<String, String> {
        val isCustom = appStateManager.state?.loginState?.useCustomClientId ?: false

        lateinit var idSecret: Pair<String, String>
        if (isCustom && isLong) {
            idSecret = Pair(
                appConfig.currentMode.environment?.longAuthCustomId ?: "",
                appConfig.currentMode.environment?.longAuthCustomSecret ?: ""
            )
        } else if (isCustom && !isLong) {
            idSecret = Pair(
                appConfig.currentMode.environment?.shortAuthCustomId ?: "",
                appConfig.currentMode.environment?.shortAuthCustomSecret ?: ""
            )
        } else if (!isCustom && isLong) {
            idSecret = Pair(
                appConfig.currentMode.environment?.longAuthId ?: "",
                appConfig.currentMode.environment?.longAuthSecret ?: ""
            )
        } else if (!isCustom && !isLong) {
            idSecret = Pair(
                appConfig.currentMode.environment?.shortAuthId ?: "",
                appConfig.currentMode.environment?.shortAuthSecret ?: ""
            )
        }
        return idSecret
    }

    @Throws(Exception::class)
    override fun decodeJWTBody(JWTEncoded: String): JSONObject {
        val split = JWTEncoded.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        // val jwtHeaderJson = JSONObject(getJson(split[0]))
        val jwtBodyJson = JSONObject(getJson(split[1]))
        // Timber.e("Header: $jwtHeaderJson")
        Timber.e("Body: $jwtBodyJson")
        return jwtBodyJson
    }

    @Throws(UnsupportedEncodingException::class)
    private fun getJson(strEncoded: String): String {
        val decodedBytes = Base64.decode(strEncoded, Base64.URL_SAFE)
        return String(decodedBytes)
    }
}