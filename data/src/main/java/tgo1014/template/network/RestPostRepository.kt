package tgo1014.template.network

import tgo1014.template.models.Post
import tgo1014.template.repositories.PostRepository
import tgo1014.template.repositories.RepositoryException

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