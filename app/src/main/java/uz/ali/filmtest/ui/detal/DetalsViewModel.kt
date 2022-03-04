package uz.ali.filmtest.ui.detal

import android.util.Log
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import uz.ali.filmtest.models.ModelDetals
import uz.ali.filmtest.models.ModelImagesVideos
import uz.ali.filmtest.models.ModelInfoActor
import uz.ali.filmtest.models.ModelVideo
import uz.ali.filmtest.repository.Repository
import javax.inject.Inject

@HiltViewModel
class DetalsViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    var getInfoFilm = MutableLiveData<ModelDetals>()
    var getInfoActor = MutableLiveData<ModelInfoActor>()
    var getVideos = MutableLiveData<ModelVideo>()
    var getVideosImages = MutableLiveData<ModelImagesVideos>()

    fun getVideosImages(id:String) =
        viewModelScope.launch {
            try {
                repository.getVideosImages(id).let {
                    if (it.isSuccessful) {
                        getVideosImages.postValue(it.body())
                        Log.d("message", "${it.code()}")
                        Log.d("message", "${it.body()}")
                    } else {
                        Log.d(
                            "message",
                            "getTokenId: not token ${it.code()} ${it.message()}  ${it.raw()} "
                        )
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    fun getInfoFilm(id:String) =
        viewModelScope.launch {
            try {
                repository.getInfoFilm(id).let {
                    if (it.isSuccessful) {
                        getInfoFilm.postValue(it.body())
                        Log.d("message", "${it.code()}")
                        Log.d("message", "${it.body()?.popularity}")
                    } else {
                        Log.d(
                            "message",
                            "getTokenId: not token ${it.code()} ${it.message()}  ${it.raw()} "
                        )
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    fun getInfoActor(id:String) =
        viewModelScope.launch {
            try {
                repository.getInfoActor(id).let {
                    if (it.isSuccessful) {
                        getInfoActor.postValue(it.body())
                        Log.d("message", "${it.code()}")
                        Log.d("message", "${it.body()}")
                    } else {
                        Log.d(
                            "message",
                            "getTokenId: not token ${it.code()} ${it.message()}  ${it.raw()} "
                        )
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    fun getVideos(id:String) =
        viewModelScope.launch {
            try {
                repository.getVideos(id).let {
                    if (it.isSuccessful) {
                        getVideos.postValue(it.body())
                        Log.d("message", "${it.code()}")
                        Log.d("message", "${it.body()}")
                    } else {
                        Log.d(
                            "message",
                            "getTokenId: not token ${it.code()} ${it.message()}  ${it.raw()} "
                        )
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
}