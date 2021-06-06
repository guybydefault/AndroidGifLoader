package ru.guybydefault.minin.domain


data class Image(
    val id: Int,
    val description: String,
    val gifURL: String,
    val type: String,
    val width: Int,
    val height: Int,
    val author: String
)
