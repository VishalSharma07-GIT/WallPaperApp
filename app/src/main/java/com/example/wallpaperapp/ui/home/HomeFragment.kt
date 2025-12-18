package com.example.wallpaperapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wallpaperapp.R
import com.example.wallpaperapp.databinding.FragmentHomeBinding
import com.example.wallpaperapp.domain.model.WallPaper
import com.example.wallpaperapp.ui.detail.WallpaperDetailFragment
import com.example.wallpaperapp.utils.UiState
import com.google.android.material.internal.ViewUtils.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var favoriteIds=setOf<Int>()

  private var _binding: FragmentHomeBinding?=null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var wallpaperAdapter: WallpaperAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnHome.setImageResource(R.drawable.ic_home_filled)
        binding.btnFavorites.setImageResource(R.drawable.ic_favorite_border)

        setupRecyclerView()
        observeWallpapers()
        setupRetryButton()

        binding.btnSearch.setOnClickListener {
            binding.searchBarLayout.visibility = View.VISIBLE
            binding.fadeOverlay.visibility = View.VISIBLE
            binding.btnSearch.visibility= View.GONE

            binding.etSearch.requestFocus()
            showSearchBar()
        }

        binding.btnCloseSearch.setOnClickListener {
            binding.searchBarLayout.visibility = View.GONE
            binding.fadeOverlay.visibility = View.GONE
            binding.btnSearch.visibility= View.VISIBLE

            binding.etSearch.setText("")
            binding.etSearch.text.clear()
            binding.noResultsLayout.visibility = View.GONE

            hideKeyboard()
            viewModel.loadWallpapers()
            binding.etSearch.text.clear()
            hideSearchBar()
            viewModel.loadWallpapers()

        }

        binding.etSearch.addTextChangedListener{ text ->
            binding.btnCloseSearch.visibility=
                if(text.isNullOrEmpty()) View.GONE else View.VISIBLE
        }
        binding.btnFavorites.setOnClickListener {
            findNavController().navigate(R.id.favoritesFragment)
        }





        binding.etSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                val query = binding.etSearch.text.toString().trim()
                hideKeyboard()

                binding.noResultsLayout.visibility = View.GONE


                if (query.isNotEmpty()) {
                    hideKeyboard()
                    viewModel.search(query)
                } else {

                    viewModel.loadWallpapers()
                }

                binding.fadeOverlay.visibility = View.GONE
                true
            } else false
        }

    }

    private fun openWallpaperDetail(wallpaper: WallPaper){
        val bundle = Bundle().apply {
            putString("imageUrl", wallpaper.imageUrl)
            putString("description", wallpaper.description)
        }
     findNavController().navigate(R.id.wallpaperDetailFragment, bundle)
    }

    private fun showSearchBar() {
        binding.searchBarLayout.apply {
            alpha = 0f
            translationY = -40f
            visibility = View.VISIBLE

            animate()
                .alpha(1f)
                .translationY(0f)
                .setDuration(250)
                .start()
        }


        binding.fadeOverlay.apply {
            alpha = 0f
            visibility = View.VISIBLE
            animate()
                .alpha(1f)
                .setDuration(250)
                .start()
        }
    }

    private fun hideSearchBar() {
        binding.searchBarLayout.animate()
            .alpha(0f)
            .translationY(-40f)
            .setDuration(250)
            .withEndAction {
                binding.searchBarLayout.visibility = View.GONE
            }
            .start()

        binding.fadeOverlay.animate()
            .alpha(0f)
            .setDuration(250)
            .withEndAction {
                binding.fadeOverlay.visibility = View.GONE

            }

            .start()
    }

    private fun hideKeyboard(){
        val imm = requireContext().getSystemService(android.content.Context.INPUT_METHOD_SERVICE)
                as android.view.inputmethod.InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }


    private fun setupRecyclerView(){
        wallpaperAdapter = WallpaperAdapter(
            onItemClick = { wallpaper ->
                val bundle = Bundle().apply {
                    putInt("id", wallpaper.id)
                    putString("imageUrl", wallpaper.imageUrl)
                    putString("description", wallpaper.description)
                }
                findNavController().navigate(R.id.wallpaperDetailFragment, bundle)
            },
            isFavorite = { id ->
                favoriteIds.contains(id)
            }
        )

        binding.rvWallpapers.adapter=wallpaperAdapter
        binding.rvWallpapers.layoutManager= GridLayoutManager(requireContext(),3)

        binding.rvWallpapers.apply {
            layoutManager= LinearLayoutManager(requireContext())
            adapter=wallpaperAdapter
        }
    }

    private fun observeWallpapers(){
        viewLifecycleOwner.lifecycleScope.launch {

            viewModel.wallpaperState.collectLatest { state ->
                when(state){

                    is UiState.Loading ->{
                        binding.shimmerLayout.visibility= View.VISIBLE
                        binding.rvWallpapers.visibility=View.GONE
                        binding.errorLayout.visibility= View.GONE
                    }
                    is UiState.Success ->{
                        binding.shimmerLayout.visibility= View.GONE
                        binding.errorLayout.visibility= View.GONE

                        if(state.data.isEmpty()){
                            binding.rvWallpapers.visibility=View.GONE
                            binding.noResultsLayout.visibility=View.VISIBLE
                        }else{

                            binding.noResultsLayout.visibility=View.GONE
                            binding.rvWallpapers.visibility=View.VISIBLE
                            wallpaperAdapter.submitList(state.data)

                        }

                    }
                    is UiState.Error ->{
                        binding.shimmerLayout.visibility= View.GONE
                        binding.rvWallpapers.visibility=View.GONE
                        binding.errorLayout.visibility= View.VISIBLE
                    }
                }

            }
        }
    }
    private fun setupRetryButton(){
        binding.btnRetry.setOnClickListener {
            viewModel.loadWallpapers()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}
