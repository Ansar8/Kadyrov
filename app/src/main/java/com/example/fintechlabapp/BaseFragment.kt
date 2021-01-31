package com.example.fintechlabapp

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.fintechlabapp.api.StoryResponse

class BaseFragment() : Fragment(R.layout.fragment_layout) {

    private lateinit var progressBar: ProgressBar
    private lateinit var prevButton: ImageButton
    private lateinit var nextButton: ImageButton
    private lateinit var retryButton: TextView
    private lateinit var errorImageView: ImageView
    private lateinit var errorTextView: TextView
    private lateinit var pictureContainer: CardView
    private lateinit var picture: ImageView
    private lateinit var title: TextView
    private lateinit var type: String

    private val viewModel: StoriesViewModel by viewModels { StoriesViewModelFactory() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            type = it.getString(PARAM_TYPE, "latest")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews(view)
        viewModel.storyInfo.observe(viewLifecycleOwner, this::showResult)

        if (savedInstanceState == null){
            viewModel.getNextStory(type)
        }
    }

    private fun initViews(view: View) {
        progressBar = view.findViewById(R.id.progress_bar)
        pictureContainer = view.findViewById(R.id.picture_container)
        errorImageView = view.findViewById(R.id.error_icon)
        errorTextView = view.findViewById(R.id.error_text)

        nextButton = view.findViewById(R.id.next_btn)
        nextButton.setOnClickListener{ viewModel.getNextStory(type) }

        prevButton = view.findViewById(R.id.prev_btn)
        prevButton.setOnClickListener { viewModel.getPrevStory() }

        retryButton = view.findViewById(R.id.retry_btn)
        retryButton.setOnClickListener { viewModel.getNextStory(type) }

        picture = view.findViewById(R.id.picture)
        title = view.findViewById(R.id.title)
    }

    private fun showResult(result: Result<StoryResponse>){
        when(result){
            is Result.Success -> {
                updateStory(result.data)
                showStoryInfo(true)
                showErrorMessage(false)
            }
            is Result.Error -> {
                showStoryInfo(false)
                showProgressBar(false)
                showErrorMessage(true)

            }
            is Result.Loading -> {
                showStoryInfo(false)
                showProgressBar(true)
                showErrorMessage(false)
            }
        }
    }

    private fun updateStory(story: StoryResponse?) {
        Glide.with(requireContext())
            .asGif()
            .load(story?.pictureUrl)
            .listener(object: RequestListener<GifDrawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<GifDrawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    showProgressBar(false)
                    return false
                }

                override fun onResourceReady(
                    resource: GifDrawable?,
                    model: Any?,
                    target: Target<GifDrawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    showProgressBar(false)
                    return false
                }
            })
            .transition(DrawableTransitionOptions.withCrossFade(500))
            .error(R.drawable.ic_error)
            .into(picture)

        title.text = story?.title ?: ""
    }

    private fun showProgressBar(isVisible: Boolean) {
        progressBar.isVisible = isVisible
    }

    private fun showStoryInfo(isVisible: Boolean) {
        pictureContainer.isVisible = isVisible
        nextButton.isVisible = isVisible
        prevButton.isVisible = isVisible
    }

    private fun showErrorMessage(isVisible: Boolean) {
        errorImageView.isVisible = isVisible
        errorTextView.isVisible = isVisible
        retryButton.isVisible = isVisible
    }

    companion object{
        private const val PARAM_TYPE = "type"

        fun newInstance(type: String): BaseFragment {
            val fragment = BaseFragment()
            val args = Bundle()
            args.putString(PARAM_TYPE, type)
            fragment.arguments = args
            return fragment
        }
    }
}