package ru.guybydefault.minin.repository

import ru.guybydefault.minin.domain.Image

class ImageIterator(val imageRepository: ImageRepository) {
    companion object {
        const val IMAGES_ON_PAGE = 4
    }

    var currImageIndex = 0
        private set

    fun getPageIndex(index: Int): Int {
        return index / IMAGES_ON_PAGE
    }

    fun getOnPageIndex(index: Int): Int {
        return index % IMAGES_ON_PAGE
    }

    suspend fun getImage(index: Int): Image? {
        return imageRepository.getTopImages(getPageIndex(index))?.getOrNull(getOnPageIndex(index))
    }

    suspend fun currentImage(): Image? {
        return getImage(currImageIndex)
    }

    suspend fun nextImage(): Image? {
        val nextImage = getImage(currImageIndex + 1)
        if (nextImage != null) {
            currImageIndex++
        }
        return nextImage
    }

    suspend fun prevImage(): Image? {
        val prevImage = getImage(currImageIndex - 1)
        if (prevImage != null) {
            currImageIndex--
        }
        return prevImage
    }

    fun hasPrev(): Boolean {
        return currImageIndex > 0
    }

}