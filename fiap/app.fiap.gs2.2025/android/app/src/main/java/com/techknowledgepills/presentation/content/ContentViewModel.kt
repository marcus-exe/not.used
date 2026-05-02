package com.techknowledgepills.presentation.content

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techknowledgepills.data.repository.ContentRepository
import com.techknowledgepills.domain.model.Content
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContentViewModel @Inject constructor(
    private val contentRepository: ContentRepository
) : ViewModel() {

    private val _contents = MutableStateFlow<List<Content>>(emptyList())
    val contents: StateFlow<List<Content>> = _contents.asStateFlow()

    private val _content = MutableStateFlow<Content?>(null)
    val content: StateFlow<Content?> = _content.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    fun loadAllContent() {
        viewModelScope.launch {
            _isLoading.value = true
            contentRepository.getAllContent()
                .onSuccess {
                    _contents.value = it
                }
                .onFailure { exception ->
                    // Log error for debugging
                    android.util.Log.e("ContentViewModel", "Failed to load content", exception)
                    // Keep empty list on error
                    _contents.value = emptyList()
                }
            _isLoading.value = false
        }
    }

    fun loadContentById(id: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            contentRepository.getContentById(id).onSuccess {
                _content.value = it
            }
            _isLoading.value = false
        }
    }
}

