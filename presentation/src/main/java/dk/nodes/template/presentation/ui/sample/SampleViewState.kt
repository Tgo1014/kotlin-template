package dk.nodes.template.presentation.ui.sample

import dk.nodes.template.models.Post
import dk.nodes.template.presentation.util.SingleEvent
import dk.nodes.template.presentation.util.ViewError
import dk.nodes.template.repositories.ThemeRepository

data class SampleViewState(
    val posts: List<Post> = emptyList(),
    val viewError: SingleEvent<ViewError>? = null,
    val isLoading: Boolean = false,
    val currentTheme: ThemeRepository.Theme? = null
)