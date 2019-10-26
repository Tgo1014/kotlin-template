package dk.nodes.template.injection.modules

import dk.nodes.template.network.RestPostRepository
import dk.nodes.template.repositories.PostRepository
import org.koin.dsl.module
import org.koin.experimental.builder.singleBy

val repositoryModule = module {
    singleBy<PostRepository, RestPostRepository>()
}