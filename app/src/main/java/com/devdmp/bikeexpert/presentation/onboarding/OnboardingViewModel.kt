package com.devdmp.bikeexpert.presentation.onboarding

import androidx.lifecycle.ViewModel
import com.devdmp.data.onboarding.dto.SelectedBrandModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor() : ViewModel() {

    private val _selectedBrandModel: MutableStateFlow<SelectedBrandModel> =
        MutableStateFlow(SelectedBrandModel())
    val selectedBrandModel: StateFlow<SelectedBrandModel> =
        _selectedBrandModel.asStateFlow()

    fun updateCylinderCapacity(selectedCylinderCapacity: String) {
        _selectedBrandModel.value.setCylinderCapacitys(selectedCylinderCapacity)
    }

    fun updateBikeType(selectedBikeType: String) {
        _selectedBrandModel.value.setBikeTypes(selectedBikeType)
    }

    fun updateBrand(selectedBrand: String) {
        _selectedBrandModel.value.setBrands(selectedBrand)
    }

    fun updateModel(selectedModel: String) {
        _selectedBrandModel.value.setModels(selectedModel)
    }


}