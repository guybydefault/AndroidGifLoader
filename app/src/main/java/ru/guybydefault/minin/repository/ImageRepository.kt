package ru.guybydefault.minin.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import ru.guybydefault.minin.api.Retrofit
import ru.guybydefault.minin.domain.Image
import ru.guybydefault.minin.domain.ImagesResponse
import kotlinx.coroutines.launch
import ru.guybydefault.minin.logImagesResponse
import java.util.*

class ImageRepository {

    val topPages = Collections.synchronizedMap(hashMapOf<Int, ImagesResponse>())

    suspend fun getTopImages(page: Int): List<Image>? = coroutineScope {
        async(Dispatchers.IO) {
            if (topPages.containsKey(page)) {
                topPages[page]!!.result
            } else {
                try {
                    val resp =
                        Retrofit.imagesApi.getTopImages(page).also { logImagesResponse(page, it) }
                            .body()
                    if (resp != null) {
                        topPages[page] = resp
                    }
                    resp?.result
                } catch (t: Throwable) {
                    // TODO detailed error handling. Not enough time :(
                    null
                }
            }
        }.await()
    }
}

