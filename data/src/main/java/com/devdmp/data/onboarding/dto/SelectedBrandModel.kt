package com.devdmp.data.onboarding.dto

data class SelectedBrandModel(
    var cylinderCapacity: String? = null,
    var bikeType: String? = null,
    var brand: String? = null,
    var model: String? = null
) {
    fun setBrands(brand: String) {
        this.brand = brand
    }

    fun setModels(model: String) {
        this.model = model
    }

    fun setCylinderCapacitys(cylinderCapacity: String) {
        this.cylinderCapacity = cylinderCapacity
    }

    fun setBikeTypes(bikeType: String) {
        this.bikeType = bikeType
    }
}