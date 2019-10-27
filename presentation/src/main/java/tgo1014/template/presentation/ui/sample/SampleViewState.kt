package tgo1014.template.presentation.ui.sample

import tgo1014.template.models.Post
import tgo1014.template.presentation.util.SingleEvent
import tgo1014.template.presentation.util.ViewError
import tgo1014.template.repositories.ThemeRepository

data class SampleViewState(
    val posts: List<Post> = emptyList(),
    val viewError: SingleEvent<ViewError>? = null,
    val isLoading: Boolean = false,
    val currentTheme: ThemeRepository.Theme? = null
)