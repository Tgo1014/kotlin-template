package tgo1014.template.network

import tgo1014.template.models.Photo
import tgo1014.template.models.Post
import retrofit2.Response
import retrofit2.http.GET

interface Api {

    @GET("posts")
    suspend fun getPosts(): Response<List<Post>>

    @GET("photos")
    suspend fun getPhotos(): Response<List<Photo>>
}
