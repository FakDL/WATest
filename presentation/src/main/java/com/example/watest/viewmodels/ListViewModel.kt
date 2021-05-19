package com.example.watest.viewmodels

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.domain.interactors.LandmarkInteractor
import com.example.domain.models.Landmark
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ListViewModel @Inject constructor(
    private val interactor: LandmarkInteractor
) : ViewModel() {

    val bundle = Bundle()

    private var _viewState = MutableLiveData<ListFetchingViewState>(ListFetchingViewState.Loading)
    val viewState: LiveData<ListFetchingViewState> get() = _viewState

    suspend fun fetchData() {
        _viewState.postValue(ListFetchingViewState.Loading)
        try {
            val list = withContext(Dispatchers.IO) { interactor.getLandmark() }
            _viewState.postValue(ListFetchingViewState.Success(list))
        } catch (e: Exception) {
            _viewState.postValue(ListFetchingViewState.FetchingFailed(1))
        }
    }

    suspend fun isFavoriteLand(id: Int): Boolean = interactor.isFav(id)
    suspend fun likeLand(id: Int) = interactor.likeLand(id)
    suspend fun dislikeLand(id: Int) = interactor.dislikeLand(id)
}

sealed class ListFetchingViewState {
    data class FetchingFailed(val errorType: Int) : ListFetchingViewState()
    data class Success(val list: List<Landmark>) : ListFetchingViewState()
    object Loading: ListFetchingViewState()
}
