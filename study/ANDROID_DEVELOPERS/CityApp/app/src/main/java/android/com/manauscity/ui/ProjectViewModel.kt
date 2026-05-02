package android.com.manauscity.ui

import android.com.manauscity.data.CityDataProvider
import android.com.manauscity.model.Restaurant
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class ProjectViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(
        ProjectUiState(
            restaurantList = CityDataProvider.getRestaurantData(),
            currestRestaurant = CityDataProvider.getRestaurantData().getOrElse(0) {
                CityDataProvider.defaultRestaurant
            }

        )
    )
    val uiState: StateFlow<ProjectUiState> = _uiState

    fun updateCurrentRestaurant(selectedRestaurant: Restaurant) {
        _uiState.update {
            it.copy(currestRestaurant = selectedRestaurant)
        }
    }
    fun navigateToListPage() {
        _uiState.update {
            it.copy(isShowingListPage = true)
        }
    }

    fun navigateToDetailPage() {
        _uiState.update {
            it.copy(isShowingListPage = false)
        }
    }


}

data class ProjectUiState(
    val restaurantList: List<Restaurant> = emptyList(),
    val currestRestaurant: Restaurant = CityDataProvider.defaultRestaurant,
    val isShowingListPage: Boolean = true
)