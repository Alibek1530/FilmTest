package uz.ali.filmtest.models

data class ModelFilms(
    val page: Int,
    val results: List<ResultModel>,
    val total_pages: Int,
    val total_results: Int
)