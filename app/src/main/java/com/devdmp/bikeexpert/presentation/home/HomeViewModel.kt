package com.devdmp.bikeexpert.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devdmp.bikeexpert.BuildConfig
import com.devdmp.bikeexpert.UiState
import com.devdmp.data.onboarding.dto.SelectedBrandModel
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {
    private val _uiState: MutableStateFlow<UiState> =
        MutableStateFlow(UiState.Initial)
    val uiState: StateFlow<UiState> =
        _uiState.asStateFlow()

    private val _bikePrefsUser: MutableStateFlow<SelectedBrandModel> =
        MutableStateFlow(SelectedBrandModel())
    val bikePrefsUser: StateFlow<SelectedBrandModel> =
        _bikePrefsUser.asStateFlow()

    private val generativeModel = GenerativeModel(
        modelName = "gemini-1.5-flash",
        apiKey = BuildConfig.apiKey
    )

    fun sendPrompt(
        prompt: String
    ) {
        _uiState.value = UiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val defaultQuery =
                    "My motorcycle is a ${_bikePrefsUser.value.model} from ${_bikePrefsUser.value.brand} with a ${_bikePrefsUser.value.cylinderCapacity} range engine. I would like to ask you the following question about it:"

                val response = generativeModel.generateContent(
                    content {
                        text(defaultQuery + prompt)
                    }
                )
                response.text?.let { outputContent ->
                    _uiState.value = UiState.Success(outputContent)
                }
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.localizedMessage ?: "")

            }
        }
    }

    fun updateUserPrefs(model: String, brand: String, cylinderCapacity: String, bikeType: String) {
        _bikePrefsUser.value.setModels(model)
        _bikePrefsUser.value.setBrands(brand)
        _bikePrefsUser.value.setBikeTypes(bikeType)
        _bikePrefsUser.value.setCylinderCapacitys(cylinderCapacity)
    }
}