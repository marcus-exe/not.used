package com.techknowledgepills.presentation.recommendation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techknowledgepills.data.repository.RecommendationRepository
import com.techknowledgepills.domain.model.Content
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecommendationViewModel @Inject constructor(
    private val recommendationRepository: RecommendationRepository
) : ViewModel() {

    private val _recommendations = MutableStateFlow<List<Content>>(emptyList())
    val recommendations: StateFlow<List<Content>> = _recommendations.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    fun loadRecommendations() {
        viewModelScope.launch {
            _isLoading.value = true
            recommendationRepository.getRecommendations().onSuccess {
                _recommendations.value = it
            }
            _isLoading.value = false
        }
    }
}

