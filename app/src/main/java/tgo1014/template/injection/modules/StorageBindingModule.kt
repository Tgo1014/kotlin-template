package tgo1014.template.injection.modules

import tgo1014.template.domain.managers.PrefManager
import tgo1014.template.storage.PrefManagerImpl
import org.koin.dsl.module
import org.koin.experimental.builder.singleBy

val storageModule = module {
    singleBy<PrefManager, PrefManagerImpl>()
}