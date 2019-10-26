package dk.nodes.template.injection.modules

import dk.nodes.arch.domain.executor.Executor
import dk.nodes.arch.domain.executor.ThreadExecutor
import org.koin.dsl.module
import org.koin.experimental.builder.singleBy

val executorModule = module {
    singleBy<Executor, ThreadExecutor>()
}