package android.com.amphiniansapp.ui.screens

import android.com.amphiniansapp.AmphibiansInfoApplication
import android.com.amphiniansapp.data.AmphibiansInfoRepository
import android.com.amphiniansapp.model.AmphibianInfo
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


sealed interface AmphibiansUiState {
    data class Success(val info: List<AmphibianInfo>) : AmphibiansUiState
    object Error : AmphibiansUiState
    object Loading : AmphibiansUiState
}


class AmphibiansViewModel(
    private val amphibiansInfoRepository: AmphibiansInfoRepository
) : ViewModel() {

    var amphibiansUiState: AmphibiansUiState by mutableStateOf(AmphibiansUiState.Loading)
        private set

    init {
        getAmphibiansInfo()
    }

    fun getAmphibiansInfo() {
        viewModelScope.launch {
            amphibiansUiState = AmphibiansUiState.Loading
            amphibiansUiState = try {
                AmphibiansUiState.Success(amphibiansInfoRepository.getAmphibiansInfo())
            } catch (e: IOException) {
                AmphibiansUiState.Error
            } catch (e: HttpException) {
                AmphibiansUiState.Error
            }
        }

    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as AmphibiansInfoApplication)
                val amphibiansInfoRepository = application.container.amphibiansInfoRepository
                AmphibiansViewModel(amphibiansInfoRepository = amphibiansInfoRepository)
            }
        }
    }


}