package uz.ali.filmtest.repository

import uz.ali.filmtest.api.ApiService
import uz.ali.filmtest.contents.Contents.API_KEY
import javax.inject.Inject


class Repository @Inject
constructor(private val apiService: ApiService) {
    suspend fun getInfoFilm(id:String) = apiService.getInfoFilm(id,API_KEY)
    suspend fun getInfoActor(id:String) = apiService.getInfoActor(id,API_KEY)
    suspend fun getActorFilm(id:String) = apiService.getActorFilm(id,API_KEY)
    suspend fun getUserImages(id:String) = apiService.getUserImages(id,API_KEY)
    suspend fun getUserData(id:String) = apiService.getUserData(id,API_KEY)
    suspend fun getVideos(id:String) = apiService.getVideos(id,API_KEY)
    suspend fun getVideosImages(id:String) = apiService.getVideosImages(id,API_KEY)
}