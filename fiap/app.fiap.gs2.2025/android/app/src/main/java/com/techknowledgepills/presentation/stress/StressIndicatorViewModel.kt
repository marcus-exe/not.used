package com.techknowledgepills.presentation.stress

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techknowledgepills.data.repository.StressIndicatorRepository
import com.techknowledgepills.domain.model.StressIndicator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StressIndicatorViewModel @Inject constructor(
    private val stressIndicatorRepository: StressIndicatorRepository
) : ViewModel() {

    private val _stressIndicators = MutableStateFlow<List<StressIndicator>>(emptyList())
    val stressIndicators: StateFlow<List<StressIndicator>> = _stressIndicators.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        loadStressIndicators()
    }

    fun loadStressIndicators() {
        viewModelScope.launch {
            _isLoading.value = true
            stressIndicatorRepository.getStressIndicators().onSuccess {
                _stressIndicators.value = it
            }
            _isLoading.value = false
        }
    }

    fun generateMockData(count: Int = 30) {
        viewModelScope.launch {
            _isLoading.value = true
            stressIndicatorRepository.generateMockData(count).onSuccess {
                _stressIndicators.value = it
            }
            _isLoading.value = false
        }
    }
}

