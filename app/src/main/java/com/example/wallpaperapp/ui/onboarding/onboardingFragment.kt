package com.example.wallpaperapp.ui.onboarding

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.wallpaperapp.R
import com.example.wallpaperapp.databinding.FragmentOnboardingBinding
import com.example.wallpaperapp.utils.PreferenceManager

class OnboardingFragment : Fragment(R.layout.fragment_onboarding) {

    private var _binding: FragmentOnboardingBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentOnboardingBinding.bind(view)

        GetStartedBottomSheet()
            .show(parentFragmentManager,"GetStartedBottomSheet")

//        binding.getStartedButton.setOnClickListener {
//            val prefs= PreferenceManager(requireContext())
//            prefs.setOnboardingCompleted()
//            findNavController().navigate(R.id.action_onboarding_to_home)
//        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
