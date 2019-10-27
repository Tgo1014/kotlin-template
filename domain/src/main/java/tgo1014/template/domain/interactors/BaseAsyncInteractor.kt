package tgo1014.template.domain.interactors

interface BaseAsyncInteractor<O> {
    suspend operator fun invoke(): O
}