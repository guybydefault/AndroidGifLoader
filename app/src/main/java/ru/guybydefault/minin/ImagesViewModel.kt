package ru.guybydefault.minin

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.guybydefault.minin.domain.Image
import ru.guybydefault.minin.repository.ImageIterator

class ImagesViewModel(val imagesIterator: ImageIterator) : ViewModel() {

    val currImage = MutableLiveData<Image?>()
    var hasPrev: Boolean = false

    init {
        viewModelScope.launch {
            currImage.postValue(imagesIterator.currentImage())
        }
    }

    fun next() {
        viewModelScope.launch {
            updateImage(imagesIterator.nextImage())
        }
    }

    fun prev() {
        viewModelScope.launch {
            updateImage(imagesIterator.prevImage())
        }
    }

    private fun updateImage(image: Image?) {
        hasPrev = imagesIterator.hasPrev()
        currImage.postValue(image)
    }


}