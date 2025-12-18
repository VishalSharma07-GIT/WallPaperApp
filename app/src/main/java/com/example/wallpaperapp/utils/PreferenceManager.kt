package com.example.wallpaperapp.utils

import android.content.Context

class PreferenceManager(context: Context) {

    private val prefs = context.getSharedPreferences("waller_prefs", Context.MODE_PRIVATE)

    fun isOnboardingCompleted(): Boolean{
        return prefs.getBoolean("onboarding_done", false)

    }

    fun setOnboardingCompleted(){
        prefs.edit().putBoolean("onboarding_done", true).apply()
    }


}