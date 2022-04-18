package com.wizeline.academy.testing.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.forEach
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.android.material.snackbar.Snackbar
import com.wizeline.academy.testing.R
import com.wizeline.academy.testing.databinding.FragmentHomeBinding
import com.wizeline.academy.testing.domain.Movie
import com.wizeline.academy.testing.utils.EventObserver
import com.wizeline.academy.testing.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var moviesAdapter: MoviesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews()
        subscribeUi()
    }

    private fun initViews() {
        initToolbar()
        initRecyclerView()
    }

    private fun initToolbar() {
        NavigationUI.setupWithNavController(binding.appbar, findNavController())
        binding.appbar.menu.findItem(R.id.menu_filter)
            .setOnMenuItemClickListener {
                FilterDialog().show(childFragmentManager, "FilterDialog")
                true
            }
    }

    private fun initRecyclerView() = with(binding.moviesList) {
        setHasFixedSize(true)
        moviesAdapter = MoviesAdapter(viewModel::onMovieClick)
        adapter = moviesAdapter
    }

    private fun subscribeUi() {
        viewModel.movies.observe(viewLifecycleOwner) {
            with(binding) {
                progressBar.isVisible = it is Resource.Loading
                moviesList.isVisible = it is Resource.Success
                errorMessage.isVisible = it is Resource.Failure
            }
            when (it) {
                Resource.Loading -> {}
                is Resource.Success -> renderMovies(it.data)
                is Resource.Failure -> renderFailure(it.error)
            }
        }
        viewModel.navigateDetails.observe(viewLifecycleOwner, EventObserver {
            val action = HomeFragmentDirections.actionHomeFragmentToDetailsFragment(it)
            findNavController().navigate(action)
        })
    }

    private fun renderMovies(movies: List<Movie>) {
        moviesAdapter.submitList(movies)
        with(binding.errorMessage) {
            isVisible = movies.isEmpty()
            text = getString(R.string.no_movies)
        }
    }

    private fun renderFailure(error: Throwable) {
        binding.errorMessage.text = getString(R.string.default_error_message, error.message)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}