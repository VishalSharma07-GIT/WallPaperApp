package com.example.wallpaperapp.ui.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.wallpaperapp.R
import com.example.wallpaperapp.utils.PreferenceManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : Fragment(R.layout.fragment_spalsh) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Handler(Looper.getMainLooper()).postDelayed({

            val prefs = PreferenceManager(requireContext())

            if (prefs.isOnboardingCompleted()) {
                // 🔁 User already completed onboarding
                findNavController().navigate(R.id.action_splash_to_home)
            } else {
                // 🆕 First time user
                findNavController().navigate(R.id.action_splash_to_onboarding)
            }

        }, 1500)
    }
}
