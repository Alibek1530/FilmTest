package uz.ali.filmtest.models

data class ModelImagesVideos(
    val backdrops: List<Backdrop>,
    val id: Int,
    val logos: List<Logo>,
    val posters: List<Poster>
)