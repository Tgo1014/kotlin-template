package dk.nodes.template.network

import dk.nodes.template.models.Post
import dk.nodes.template.repositories.PostRepository
import dk.nodes.template.repositories.RepositoryException

class RestPostRepository constructor(private val api: Api) : PostRepository {

    override suspend fun getPosts(cached: Boolean): List<Post> {
        val response = api.getPosts()
        if (response.isSuccessful) {
            return response.body()
                ?: throw(RepositoryException(
                    response.code(),
                    response.errorBody()?.string(),
                    response.message()
                ))
        } else {
            throw(RepositoryException(
                    response.code(),
                    response.errorBody()?.string(),
                    response.message()
            ))
        }
    }
}