package ru.guybydefault.minin

import android.util.Log
import retrofit2.Response
import ru.guybydefault.minin.domain.ImagesResponse

const val REPOSITORY_TAG = "ImageRepository"

fun logImagesResponse(page: Int, r: Response<ImagesResponse>) {
    val images = r.body()
    if (!r.isSuccessful || images == null) {
        Log.e(
            REPOSITORY_TAG,
            "Failed to load images for page ${page}: status ${r.code()}, msg ${r.message()}"
        )
    } else {
        Log.i(REPOSITORY_TAG, "Loaded {${images.result.size} images from page {$page}")
    }
}