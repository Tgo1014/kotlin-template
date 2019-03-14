package dk.nodes.template.network.rest

import dk.nodes.template.domain.models.Post

class RestPostRepository(private val api: Api) :
    dk.nodes.template.domain.repositories.PostRepository {
    @Throws(dk.nodes.template.domain.repositories.RepositoryException::class)
    override suspend fun getPosts(cached: Boolean): List<Post> {
        val response = api.getPosts().execute()
        if (response.isSuccessful) {
            return response.body()
                ?: throw(dk.nodes.template.domain.repositories.RepositoryException(
                    response.code(),
                    response.message()
                ))
        }
        throw(dk.nodes.template.domain.repositories.RepositoryException(
            response.code(),
            response.message()
        ))
    }
}