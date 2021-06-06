package ru.guybydefault.minin

import ru.guybydefault.minin.repository.ImageIterator
import ru.guybydefault.minin.repository.ImageRepository

class AppContainer {
    val imageRepository = ImageRepository()
    val imageIterator = ImageIterator(imageRepository)
    val imagesViewModel = ImagesViewModel(imageIterator)
}