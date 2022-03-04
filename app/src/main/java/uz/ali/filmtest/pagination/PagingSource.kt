package uz.ali.filmtest.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import retrofit2.Response
import uz.ali.filmtest.api.ApiService
import uz.ali.filmtest.contents.Contents.API_KEY
import uz.ali.filmtest.models.ModelFilms
import uz.ali.filmtest.models.ResultModel

class PagingSource(
    private val apiService: ApiService,
    private val type:Int
) : PagingSource<Int, ResultModel>() {

    override fun getRefreshKey(state: PagingState<Int, ResultModel>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>):
            LoadResult<Int, ResultModel> {

        return try {
            val currentPage = params.key ?: 1
            var response:Response<ModelFilms> ?=null
            if (type==1){
                response = apiService.getPopular(currentPage,API_KEY)
            }
            if (type==2){
                response = apiService.getTopRated(currentPage,API_KEY)
            }
            if (type==3){
                response = apiService.getUpcoming(currentPage,API_KEY)
            }

            val responseData = mutableListOf<ResultModel>()
            val data = response?.body()?.results ?: emptyList()

            responseData.addAll(data)
            LoadResult.Page(
                data = responseData,
                prevKey = if (currentPage == 1) null else -1,
                nextKey = currentPage.plus(1)
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }

    }
}