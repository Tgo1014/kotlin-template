package tgo1014.template.repositories

import tgo1014.template.models.Post

interface PostRepository {
    suspend fun getPosts(cached: Boolean = false): List<Post>
}