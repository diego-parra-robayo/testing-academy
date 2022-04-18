package com.wizeline.academy.testing.ui.details

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IntRange
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.wizeline.academy.testing.R
import com.wizeline.academy.testing.databinding.FragmentDetailsBinding
import com.wizeline.academy.testing.domain.MovieDetails
import com.wizeline.academy.testing.utils.Resource
import com.wizeline.academy.testing.utils.loadImage
import com.wizeline.academy.testing.utils.minutesToHoursAndMinutesString
import com.wizeline.academy.testing.utils.setTextOrDefault
import dagger.hilt.android.AndroidEntryPoint
import java.lang.StringBuilder

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews()
        subscribeUi()
    }

    private fun initViews() {
        NavigationUI.setupWithNavController(
            binding.collapsingToolbar,
            binding.toolbar,
            findNavController()
        )
        binding.btnFavorite.setOnClickListener {
            viewModel.toggleFavorite()
        }
    }

    private fun subscribeUi() {
        viewModel.movieDetails.observe(viewLifecycleOwner) {
            with(binding) {
                progressBar.isVisible = it is Resource.Loading
                appbar.isVisible = it is Resource.Success
                content.isVisible = it is Resource.Success
                errorMessage.isVisible = it is Resource.Failure
            }
            when (it) {
                Resource.Loading -> {}
                is Resource.Success -> renderMovieDetails(it.data)
                is Resource.Failure -> renderFailure(it.error)
            }
        }
        viewModel.isFavorite.observe(viewLifecycleOwner) {
            binding.btnFavorite.isSelected = it
        }
    }

    private fun renderFailure(error: Throwable) {
        binding.errorMessage.text = getString(R.string.default_error_message, error.message)
    }

    private fun renderMovieDetails(movie: MovieDetails) {
        with(binding) {
            appbarImage.loadImage(movie.imageUrl, contentDescription = movie.title)
            collapsingToolbar.title = movie.title
            loadRating(movie.voteAverage)
            loadDetails(movie.voteAverage, movie.genres)
            overview.setTextOrDefault(movie.overview)
            releaseDate.setTextOrDefault(movie.releaseDate)
            loadHomepageUrl(movie.homepage)
        }
    }

    private fun loadRating(@IntRange(from = 0, to = 100) voteAverage: Int) = with(binding) {
        ratingBar.rating = voteAverage.toFloat() / 20
        ratingText.text = getString(R.string.vote_average_text, voteAverage)
    }

    private fun loadDetails(runtimeMinutes: Int, genres: List<String>) {
        val detailsString = StringBuilder()
            .append(minutesToHoursAndMinutesString(runtimeMinutes))
            .append(" • ")
            .append(genres.joinToString())
            .toString()
        binding.details.text = detailsString
    }

    private fun loadHomepageUrl(homepageUrl: String?) {
        if (!homepageUrl.isNullOrBlank()) {
            val spannable = SpannableStringBuilder(homepageUrl).apply {
                setSpan(
                    ForegroundColorSpan(Color.BLUE),
                    0,
                    length,
                    Spannable.SPAN_INCLUSIVE_INCLUSIVE
                )
                setSpan(UnderlineSpan(), 0, length, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
            }
            binding.homepageUrl.text = spannable
            binding.homepageUrl.setOnClickListener { requireActivity().openWebPage(homepageUrl) }
        } else {
            binding.homepageUrl.text = getString(R.string.label_info_not_available)
        }
    }

    private fun Activity.openWebPage(url: String) {
        val webpage: Uri = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, webpage)
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}