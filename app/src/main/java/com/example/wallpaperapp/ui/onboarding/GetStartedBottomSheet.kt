package com.example.wallpaperapp.ui.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.wallpaperapp.R
import com.example.wallpaperapp.utils.PreferenceManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class GetStartedBottomSheet : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(
            R.layout.bottom_sheet_get_started,
            container,
            false
        )

        view.findViewById<View>(R.id.btnGetStarted).setOnClickListener {

            val prefs= PreferenceManager(requireContext())
            prefs.setOnboardingCompleted()
            dismiss()
            findNavController().navigate(R.id.action_onboarding_to_home)
        }

        return view
    }
}
