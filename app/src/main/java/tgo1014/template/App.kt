package tgo1014.template

import androidx.multidex.MultiDexApplication
import tgo1014.template.injection.modules.*
import tgo1014.template.presentation.injection.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                    listOf(
                            appModule,
                            executorModule,
                            interactorModule,
                            restModule,
                            repositoryModule,
                            storageModule,
                            presentationModule
                    )
            )
        }
    }
}