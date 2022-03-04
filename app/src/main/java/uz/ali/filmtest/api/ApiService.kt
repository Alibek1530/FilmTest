package uz.ali.filmtest.api

import retrofit2.Response
import retrofit2.http.*
import uz.ali.filmtest.models.*

interface ApiService {
    @GET("movie/popular")
    suspend fun getPopular(
        @Query("page") page: Int,
        @Query("api_key") api_key: String
    ): Response<ModelFilms>

    @GET("movie/top_rated")
    suspend fun getTopRated(
        @Query("page") page: Int,
        @Query("api_key") api_key: String
    ): Response<ModelFilms>

    @GET("movie/upcoming")
    suspend fun getUpcoming(
        @Query("page") page: Int,
        @Query("api_key") api_key: String
    ): Response<ModelFilms>

    @GET("movie/{id}")
    suspend fun getInfoFilm(
        @Path("id") id: String,
        @Query("api_key") api_key: String
    ): Response<ModelDetals>

    @GET("movie/{id}/credits")
    suspend fun getInfoActor(
        @Path("id") id: String,
        @Query("api_key") api_key: String
    ): Response<ModelInfoActor>

    @GET("person/{id}/movie_credits")
    suspend fun getActorFilm(
        @Path("id") id: String,
        @Query("api_key") api_key: String
    ): Response<ModelActorFilm>

    @GET("person/{id}/images")
    suspend fun getUserImages(
        @Path("id") id: String,
        @Query("api_key") api_key: String
    ): Response<ModelImagesUser>

    @GET("person/{id}")
    suspend fun getUserData(
        @Path("id") id: String,
        @Query("api_key") api_key: String
    ): Response<ModelUserData>

    @GET("movie/{id}/videos")
    suspend fun getVideos(
        @Path("id") id: String,
        @Query("api_key") api_key: String
    ): Response<ModelVideo>

    @GET("movie/{id}/images")
    suspend fun getVideosImages(
        @Path("id") id: String,
        @Query("api_key") api_key: String
    ): Response<ModelImagesVideos>

}