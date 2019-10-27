package tgo1014.template.injection.modules

import tgo1014.template.domain.repositories.ThemeRepositoryImpl
import tgo1014.template.network.RestPostRepository
import tgo1014.template.repositories.PostRepository
import tgo1014.template.repositories.ThemeRepository
import org.koin.dsl.module
import org.koin.experimental.builder.singleBy

val repositoryModule = module {
    singleBy<PostRepository, RestPostRepository>()
    singleBy<ThemeRepository, ThemeRepositoryImpl>()
}