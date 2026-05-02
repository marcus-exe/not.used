package android.com.nasaapp.ui.screens

import android.com.nasaapp.NasaApplication
import android.com.nasaapp.data.NasaRepository
import android.com.nasaapp.model.NasaInfo
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface NasaUiState {
    data class Success(val info: List<NasaInfo>) : NasaUiState

    object Error : NasaUiState

    object Loading : NasaUiState
}

class NasaViewModel(
    private val nasaRepository: NasaRepository
) : ViewModel() {

    var nasaUiState: NasaUiState by mutableStateOf(NasaUiState.Loading)
        private set

    init {
        getNasaInfo()
    }

    fun getNasaInfo() {
        viewModelScope.launch {
            nasaUiState = NasaUiState.Loading
            nasaUiState = try {
                NasaUiState.Success(nasaRepository.getNasaInfo())
            } catch (e: IOException) {
                NasaUiState.Error
            } catch (e: HttpException) {
                NasaUiState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as NasaApplication)
                val nasaRepository = application.container.nasaRepository
                NasaViewModel(nasaRepository = nasaRepository)
            }
        }
    }


}







