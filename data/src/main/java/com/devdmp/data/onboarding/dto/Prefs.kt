package com.devdmp.data.onboarding.dto

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class Prefs @Inject constructor(@ApplicationContext private val context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    companion object {
        private const val ONBOARDING_COMPLETED_KEY = "onboarding_completed"
    }

    var onboardingCompleted: Boolean
        get() = sharedPreferences.getBoolean(ONBOARDING_COMPLETED_KEY, false)
        set(value) {
            sharedPreferences.edit().putBoolean(ONBOARDING_COMPLETED_KEY, value).apply()
        }
}