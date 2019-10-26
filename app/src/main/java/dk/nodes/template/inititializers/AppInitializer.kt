package dk.nodes.template.inititializers

import android.app.Application
import javax.inject.Inject

interface AppInitializer {
    fun init(app: Application)
}

class AppInitializerImpl @Inject constructor() : AppInitializer {
    override fun init(app: Application) {

    }
}