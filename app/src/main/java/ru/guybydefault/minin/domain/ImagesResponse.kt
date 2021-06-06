package ru.guybydefault.minin.domain


data class ImagesResponse(
    val result: List<Image>,
    val totalCount: Int
) {
}