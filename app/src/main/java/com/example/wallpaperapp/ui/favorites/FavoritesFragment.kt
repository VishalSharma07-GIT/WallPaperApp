package com.example.wallpaperapp.ui.favorites

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.wallpaperapp.R
import com.example.wallpaperapp.databinding.FragmentFavoritesBinding
import com.example.wallpaperapp.ui.home.WallpaperAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoritesFragment : Fragment(R.layout.fragment_favorites) {

    private lateinit var binding: FragmentFavoritesBinding
    private val viewModel: FavoritesViewModel by viewModels()
    private lateinit var wallpaperAdapter: WallpaperAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // ✅ ViewBinding
        binding = FragmentFavoritesBinding.bind(view)

        binding.btnFavorites.setImageResource(R.drawable.ic_favorite_filled)
        binding.btnHome.setImageResource(R.drawable.ic_home_outline)

        // 🏠 Home button → back to HomeFragment
        binding.btnHome.setOnClickListener {
            findNavController().popBackStack(R.id.homeFragment, false)
        }

        // ✅ Adapter (single-parameter version — correct for now)
        wallpaperAdapter = WallpaperAdapter(
            onItemClick = { wallpaper ->
                val bundle = Bundle().apply {
                    putInt("id", wallpaper.id)
                    putString("imageUrl", wallpaper.imageUrl)
                    putString("description", wallpaper.description)
                }
                findNavController().navigate(R.id.wallpaperDetailFragment, bundle)
            },
            isFavorite = { true }
        )


        // ✅ RecyclerView setup
        binding.rvFavorites.apply {
            layoutManager = GridLayoutManager(requireContext(), 1)
            adapter = wallpaperAdapter
        }

        // ✅ Observe favorites from Room
        lifecycleScope.launch {
            viewModel.favorites.collect { list ->
                val wallpapers = list.map { it.toWallPaper() }
                wallpaperAdapter.submitList(wallpapers)
            }
        }
    }

    // 🔥 VERY IMPORTANT: refresh when returning from Detail screen
    override fun onResume() {
        super.onResume()
        viewModel.loadFavorites()
    }
}
