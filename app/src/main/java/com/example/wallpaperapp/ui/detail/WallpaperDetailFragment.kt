package com.example.wallpaperapp.ui.detail

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import coil.load
import com.example.wallpaperapp.R
import com.example.wallpaperapp.databinding.FragmentWallpaperDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import android.content.ContentValues
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.provider.MediaStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.OutputStream
import java.net.URL


@AndroidEntryPoint
class WallpaperDetailFragment : Fragment(R.layout.fragment_wallpaper_detail) {

    // ✅ MUST be val
    private val viewModel: DetailViewModel by viewModels()

    private lateinit var binding: FragmentWallpaperDetailBinding
    private var isFavorite = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentWallpaperDetailBinding.bind(view)

        val id = arguments?.getInt("id") ?: 0
        val imageUrl = arguments?.getString("imageUrl") ?: ""
        val description = arguments?.getString("description") ?: "No description available"

        // Load image
        binding.imgFullWallpaper.load(imageUrl) {
            crossfade(true)
        }

        binding.txtDescription.text = description

        // Back
        binding.btnBack.setOnClickListener {
            requireActivity().onBackPressed()
        }

        // Share
        binding.btnShare.setOnClickListener {
            shareImage(imageUrl)
        }

        // Download
        binding.btnDownload.setOnClickListener {
            if(imageUrl.isNotEmpty()) {
                downloadImage(imageUrl)
            }else{
                Toast.makeText(
                    requireContext(),"Invalid image URL",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        // 🔹 CHECK FAVORITE STATE FROM DB
        lifecycleScope.launch {
            isFavorite = viewModel.isFavorite(id)

            binding.btnFavoriteIcon.setImageResource(
                if (isFavorite)
                    R.drawable.ic_favorite_filled
                else
                    R.drawable.ic_favorite_border
            )
        }

        // Favorite toggle (DB-backed)
        binding.btnFavorite.setOnClickListener {
            toggleFavorite(id, imageUrl, description)
        }
    }

    private fun toggleFavorite(id: Int, imageUrl: String, description: String) {

        if (isFavorite) {
            viewModel.removeFavorite(id, imageUrl, description)
            binding.btnFavoriteIcon.setImageResource(R.drawable.ic_favorite_border)
        } else {
            viewModel.addFavorite(id, imageUrl, description)
            binding.btnFavoriteIcon.setImageResource(R.drawable.ic_favorite_filled)
        }

        isFavorite = !isFavorite
    }

    private fun shareImage(url: String) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, url)
        startActivity(Intent.createChooser(intent, "Share Wallpaper"))
    }

    private fun downloadImage(imageUrl: String) {

        lifecycleScope.launch {
            binding.btnDownload.isEnabled=false
            binding.btnDownload.alpha=0.6f

            try {
                val bitmap = withContext(Dispatchers.IO) {
                    val url = URL(imageUrl)
                    BitmapFactory.decodeStream(url.openStream())
                }

                val filename = "Waller_${System.currentTimeMillis()}.jpg"

                val contentValues = ContentValues().apply {
                    put(MediaStore.Images.Media.DISPLAY_NAME, filename)
                    put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        put(
                            MediaStore.Images.Media.RELATIVE_PATH,
                            "Pictures/Waller"
                        )
                    }
                }

                val resolver = requireContext().contentResolver
                val imageUri =
                    resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

                imageUri?.let { uri ->
                    val outputStream: OutputStream? =
                        resolver.openOutputStream(uri)

                    outputStream.use {
                        it?.let { stream -> bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream) }
                    }

                    Toast.makeText(
                        requireContext(),
                        "Wallpaper saved to Gallery",
                        Toast.LENGTH_SHORT
                    ).show()
                    binding.btnDownload.isEnabled=true
                    binding.btnDownload.alpha=1f
                }

            }
//            binding.btnDownload.isEnabled=true
//            binding.btnDownload.alpha=1f

            catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(
                    requireContext(),
                    "Failed to save wallpaper",
                    Toast.LENGTH_SHORT
                ).show()
                binding.btnDownload.isEnabled = true
                binding.btnDownload.alpha = 1f

            }
        }
    }

}
