package com.template.app.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.template.app.domain.model.PlaceholderModel
import com.template.app.domain.repository.PlaceholderRepository
import com.template.app.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: PlaceholderRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<PlaceholderModel>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<PlaceholderModel>>> = _uiState.asStateFlow()

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            repository.getPlaceholders()
                .catch { e ->
                    _uiState.value = UiState.Error(e.message ?: "")
                }
                .collect { items ->
                    if (items.isEmpty()) {
                        _uiState.value = UiState.Empty
                    } else {
                        _uiState.value = UiState.Success(items)
                    }
                }
        }
    }

    // TODO: Example function
    fun addData(name: String) {
        viewModelScope.launch {
            repository.addPlaceholder(name)
        }
    }
}
