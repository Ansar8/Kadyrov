package com.example.fintechlabapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.fintechlabapp.api.StoryResponse

class LatestFragment : Fragment(R.layout.fragment_latest) {

    private lateinit var progressBar: ProgressBar
    private lateinit var prevButton: ImageButton
    private lateinit var nextButton: ImageButton
    private lateinit var retryButton: TextView
    private lateinit var errorMessage: LinearLayout
    private lateinit var pictureContainer: CardView
    private lateinit var picture: ImageView
    private lateinit var title: TextView

    private val viewModel: StoriesViewModel by viewModels { StoriesViewModelFactory() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews(view)
        viewModel.storyInfo.observe(viewLifecycleOwner, this::showResult)

        if (savedInstanceState == null){
            viewModel.loadStory()
        }
    }

    private fun initViews(view: View) {
        progressBar = view.findViewById(R.id.progress_bar)
        pictureContainer = view.findViewById(R.id.picture_container)
        errorMessage = view.findViewById(R.id.error_message)

        nextButton = view.findViewById(R.id.next_btn)
        nextButton.setOnClickListener{ viewModel.loadStory() }

        prevButton = view.findViewById(R.id.prev_btn)
        prevButton.setOnClickListener {  }

        retryButton = view.findViewById(R.id.retry_btn)
        retryButton.setOnClickListener { viewModel.loadStory() }

        picture = view.findViewById(R.id.picture)
        title = view.findViewById(R.id.title)
    }

    private fun showResult(result: Result<StoryResponse>){
        when(result){
            is Result.Success -> {
                updateStory(result.data)
                showStoryInfo(true)
                showProgressBar(false)
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
            .transition(DrawableTransitionOptions.withCrossFade(500))
            .error(R.drawable.ic_image)
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
        errorMessage.isVisible = isVisible
    }
}