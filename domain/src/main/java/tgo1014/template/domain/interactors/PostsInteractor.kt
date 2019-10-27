package tgo1014.template.domain.interactors

import tgo1014.template.models.Post
import tgo1014.template.repositories.PostRepository

class PostsInteractor constructor(
    private val postRepository: PostRepository
) : BaseAsyncInteractor<List<Post>> {

    override suspend fun invoke(): List<Post> {
        return postRepository.getPosts(true)
    }
}