package com.example.watest.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.domain.interactors.LandmarkInteractor
import com.example.domain.models.Landmark
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DetailsViewModel @Inject constructor(
    private val interactor: LandmarkInteractor
) : ViewModel() {

    private var _viewState = MutableLiveData<DetailsFetchingViewState>()
    val viewState: LiveData<DetailsFetchingViewState> get() = _viewState

    suspend fun getLandById(id: Int){
        _viewState.postValue(DetailsFetchingViewState.Loading)
        try {
            val land = withContext(Dispatchers.IO) { interactor.getLandById(id) }
            if (land != null) {_viewState.postValue(DetailsFetchingViewState.Success(land))}
            else {_viewState.postValue(DetailsFetchingViewState.FetchingFailed(0))}
        } catch (e: Exception) {
            _viewState.postValue(DetailsFetchingViewState.FetchingFailed(1))
        }
    }

    suspend fun isFavoriteLand(id: Int): Boolean = interactor.isFav(id)
    suspend fun likeLand(id: Int) = interactor.likeLand(id)
    suspend fun dislikeLand(id: Int) = interactor.dislikeLand(id)

}
sealed class DetailsFetchingViewState {
    data class FetchingFailed(val errorType: Int) : DetailsFetchingViewState()
    data class Success(val landmark: Landmark) : DetailsFetchingViewState()
    object Loading: DetailsFetchingViewState()
}