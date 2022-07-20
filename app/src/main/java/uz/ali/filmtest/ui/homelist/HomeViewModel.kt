package uz.ali.filmtest.ui.homelist

import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import uz.ali.filmtest.api.ApiService
import uz.ali.filmtest.pagination.PagingSource
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val apiService: ApiService
) : ViewModel() {

    var listPopular = Pager(PagingConfig(pageSize = 1)) { //pageSize boshlangich page
        PagingSource(apiService,1)
    }.flow.cachedIn(viewModelScope)


    var listTopRated = Pager(PagingConfig(pageSize = 1)) {
        PagingSource(apiService,2)
    }.flow.cachedIn(viewModelScope)

    var listUpcoming = Pager(PagingConfig(pageSize = 1)) {
        PagingSource(apiService,3)
    }.flow.cachedIn(viewModelScope)
}