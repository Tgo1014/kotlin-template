package dk.nodes.template.injection.modules

import com.google.gson.GsonBuilder
import dk.nodes.template.BuildConfig
import dk.nodes.template.network.Api
import dk.nodes.template.network.util.BufferedSourceConverterFactory
import dk.nodes.template.network.util.DateDeserializer
import dk.nodes.template.network.util.ItemTypeAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.experimental.builder.single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit

val restModule = module {

    single<ItemTypeAdapterFactory>()
    single<DateDeserializer>()

    single { GsonConverterFactory.create(get()) }
    single(named("NAME_BASE_URL")) { "https://jsonplaceholder.typicode.com" /*BuildConfig.API_URL*/ }

    single {
        GsonBuilder()
                .registerTypeAdapterFactory(get())
                .registerTypeAdapter(Date::class.java, get())
                .setDateFormat(DateDeserializer.DATE_FORMATS[0])
                .create()
    }
    single {
        val clientBuilder = OkHttpClient.Builder()
                .connectTimeout(45, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            clientBuilder.addInterceptor(logging)
        }
        clientBuilder.build()
    }
    single {
        Retrofit.Builder()
                .client(get())
                .baseUrl(get<String>(named("NAME_BASE_URL")))
                .addConverterFactory(BufferedSourceConverterFactory())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    single {
        val retrofit: Retrofit by inject()
        retrofit.create<Api>(Api::class.java)
    }
}
