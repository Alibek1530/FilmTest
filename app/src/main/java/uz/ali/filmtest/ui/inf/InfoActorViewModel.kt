package uz.ali.filmtest.ui.inf

import android.util.Log
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import uz.ali.filmtest.models.*
import uz.ali.filmtest.repository.Repository
import javax.inject.Inject

@HiltViewModel
class InfoActorViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    var getActorFilm = MutableLiveData<ModelActorFilm>()
    var getUserImages = MutableLiveData<ModelImagesUser>()
    var getUserData = MutableLiveData<ModelUserData>()

    fun getActorFilm(id: String) =
        viewModelScope.launch {
            try {
                repository.getActorFilm(id).let {
                    if (it.isSuccessful) {
                        getActorFilm.postValue(it.body())
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

    fun getUserImages(id: String) =
        viewModelScope.launch {
            try {
                repository.getUserImages(id).let {
                    if (it.isSuccessful) {
                        getUserImages.postValue(it.body())
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

    fun getUserData(id: String) =
        viewModelScope.launch {
            try {
                repository.getUserData(id).let {
                    if (it.isSuccessful) {
                        getUserData.postValue(it.body())
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