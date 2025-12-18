package com.example.wallpaperapp.data.remote.dto

data class PhotoDto(
    val id: Int,
    val photographer: String?,
    val src: SrcDto,
    val alt: String?
)
