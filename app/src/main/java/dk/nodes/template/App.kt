package dk.nodes.template

import androidx.multidex.MultiDexApplication
import dk.nodes.template.injection.modules.*
import dk.nodes.template.presentation.injection.presentationModule
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