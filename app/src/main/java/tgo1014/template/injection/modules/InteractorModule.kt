package tgo1014.template.injection.modules

import tgo1014.template.domain.interactors.PostsInteractor
import org.koin.dsl.module
import org.koin.experimental.builder.single

val interactorModule = module {
    single<PostsInteractor>()
}