package dk.nodes.template.injection.modules

import dk.nodes.template.domain.managers.PrefManager
import dk.nodes.template.storage.PrefManagerImpl
import org.koin.dsl.module
import org.koin.experimental.builder.singleBy

val storageModule = module {
    singleBy<PrefManager, PrefManagerImpl>()
}