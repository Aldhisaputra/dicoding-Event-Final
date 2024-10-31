package com.bangkit.event.ui.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelFactorySetting(
    private val preferences: SettingPreferences
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingViewModel::class.java)) {
            return SettingViewModel(preferences) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactorySetting? = null

        fun getInstance(preferences: SettingPreferences): ViewModelFactorySetting =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactorySetting(preferences)
            }.also { instance = it }
    }
}
