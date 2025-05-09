package com.toluleke.composesolution

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.toluleke.common.ViewState
import com.toluleke.common.remote.CountriesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ComposeViewModel @Inject constructor(
    private val repository: CountriesRepository
) : ViewModel() {

    private var _viewState = MutableStateFlow(ViewState())
    val viewState = _viewState.asStateFlow()

    init {
        viewModelScope.launch {
            withContext(Dispatchers.Unconfined) {

                loadCountries()

            }
        }

    }

    private suspend fun loadCountries() {
        try {
            val response = repository.getItems()
            response.collect { countries ->
                _viewState.update {
                    it.copy(
                        countries = countries
                    )
                }
            }

        } catch (e: Exception) {
            _viewState.update {
                it.copy(
                    error = true,
                    errorMessage = e.message.toString()
                )
            }
        }
    }
}