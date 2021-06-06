package ru.guybydefault.minin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import ru.guybydefault.minin.databinding.ActivityMainBinding
import ru.guybydefault.minin.domain.Image

class MainActivity : AppCompatActivity() {

    private lateinit var container: AppContainer
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: ImagesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        container = (application as MyApplication).appContainer
        viewModel = container.imagesViewModel

        viewModel.currImage.observe(this) { image ->
            loadImage(image)
        }

        binding.nextBtn.setOnClickListener {
            showProgressBar(getString(R.string.progress_bar_loading_img_info))
            viewModel.next()
        }
        binding.prevBtn.setOnClickListener {
            viewModel.prev()
        }
    }

    private fun loadImage(image: Image?) {
        if (image == null) {
            onError(getString(R.string.error_image_indexing))
        } else {
            setProgressBarText(getString(R.string.progress_bar_loading_gif))
            Glide
                .with(this)
                .asGif()
                .placeholder(R.drawable.ic_comment_arrow_right)
                .apply(RequestOptions().override(image.width, image.height))
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .load(image.gifURL)
                .addListener(object : RequestListener<GifDrawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<GifDrawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        onError(getString(R.string.error_gif_downloading))
                        return false
                    }

                    override fun onResourceReady(
                        resource: GifDrawable?,
                        model: Any?,
                        target: Target<GifDrawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        hideProgressBar()
                        return false
                    }
                })
                .into(binding.gifView)
            binding.prevBtn.isClickable = viewModel.hasPrev
        }
    }


    private var toast: Toast? = null
    private fun onError(errorMsg: String) {
        hideProgressBar()
        if (toast != null) {
            toast!!.cancel()
        }
        toast = Toast.makeText(this, errorMsg, Toast.LENGTH_LONG)
        toast!!.show()
    }

    private fun setProgressBarText(progressBarText: String) {
        binding.progressBarText.text = progressBarText

    }

    private fun showProgressBar(progressBarText: String) {
        binding.imageProgressBar.visibility = View.VISIBLE
        binding.progressBarText.visibility = View.VISIBLE
        setProgressBarText(progressBarText)
        binding.gifView.visibility = View.INVISIBLE
    }

    private fun hideProgressBar() {
        binding.imageProgressBar.visibility = View.INVISIBLE
        binding.progressBarText.visibility = View.INVISIBLE
        binding.gifView.visibility = View.VISIBLE
    }


}