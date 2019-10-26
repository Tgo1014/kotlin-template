package dk.nodes.template.injection.modules

import dk.nodes.template.domain.interactors.PostsInteractor
import org.koin.dsl.module
import org.koin.experimental.builder.single

val interactorModule = module {
    single<PostsInteractor>()
}