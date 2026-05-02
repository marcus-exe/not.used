package com.techknowledgepills.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techknowledgepills.data.repository.RecommendationRepository
import com.techknowledgepills.data.repository.StressIndicatorRepository
import com.techknowledgepills.domain.model.Content
import com.techknowledgepills.domain.model.StressIndicator
import com.techknowledgepills.domain.model.StressLevel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val stressIndicatorRepository: StressIndicatorRepository,
    private val recommendationRepository: RecommendationRepository
) : ViewModel() {

    private val _latestStress = MutableStateFlow<StressIndicator?>(null)
    val latestStress: StateFlow<StressIndicator?> = _latestStress.asStateFlow()

    private val _recommendations = MutableStateFlow<List<Content>>(emptyList())
    val recommendations: StateFlow<List<Content>> = _recommendations.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            _isLoading.value = true
            stressIndicatorRepository.getLatestStressIndicator().onSuccess {
                _latestStress.value = it
            }
            recommendationRepository.getRecommendations().onSuccess {
                _recommendations.value = it
            }
            _isLoading.value = false
        }
    }

    fun getStressLevelColor(stressLevel: StressLevel): androidx.compose.ui.graphics.Color {
        return when (stressLevel) {
            StressLevel.Low -> com.techknowledgepills.presentation.theme.StressLow
            StressLevel.Medium -> com.techknowledgepills.presentation.theme.StressMedium
            StressLevel.High -> com.techknowledgepills.presentation.theme.StressHigh
            StressLevel.Critical -> com.techknowledgepills.presentation.theme.StressCritical
        }
    }
}

