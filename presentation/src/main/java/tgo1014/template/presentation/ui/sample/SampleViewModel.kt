package tgo1014.template.presentation.ui.sample

import androidx.lifecycle.viewModelScope
import tgo1014.template.domain.interactors.*
import tgo1014.template.models.Post
import tgo1014.template.presentation.extensions.asResult
import tgo1014.template.presentation.ui.base.BaseViewModel
import tgo1014.template.presentation.util.SingleEvent
import tgo1014.template.presentation.util.ViewErrorController
import tgo1014.template.repositories.ThemeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SampleViewModel constructor(
        postsInteractor: PostsInteractor,
        private val themeRepository: ThemeRepository
) : BaseViewModel<SampleViewState>() {

    override val initState: SampleViewState = SampleViewState()

    private val resultInteractor = postsInteractor.asResult()

    fun fetchPosts() = viewModelScope.launch(Dispatchers.Main) {
        state = mapResult(Loading())
        val result = withContext(Dispatchers.IO) { resultInteractor.invoke() }
        state = mapResult(result)
    }

    fun setTheme() {
        state = state.copy(currentTheme = themeRepository.getTheme())
    }

    fun switchClicked() {
        val theme = if (themeRepository.getTheme() == ThemeRepository.Theme.DARK) {
            ThemeRepository.Theme.LIGHT
        } else ThemeRepository.Theme.DARK

        themeRepository.setTheme(theme)
        state = state.copy(currentTheme = theme)
    }

    private fun mapResult(result: InteractorResult<List<Post>>): SampleViewState {
        return when (result) {
            is Success -> state.copy(posts = result.data, isLoading = false)
            is Loading -> state.copy(isLoading = true)
            is Fail -> state.copy(
                    viewError = SingleEvent(ViewErrorController.mapThrowable(result.throwable)),
                    isLoading = false
            )
            else -> SampleViewState()
        }
    }
}