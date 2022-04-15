package com.wizeline.academy.testing.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.wizeline.academy.testing.databinding.ListItemMovieBinding
import com.wizeline.academy.testing.domain.Movie
import com.wizeline.academy.testing.utils.loadImage

class MoviesAdapter(
    private val onItemClickListener: (movieId: String) -> Unit
) : ListAdapter<Movie, MoviesAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent, onItemClickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class ViewHolder private constructor(
        private val binding: ListItemMovieBinding,
        onItemClickListener: (movieId: String) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        private var movie: Movie? = null

        init {
            binding.root.setOnClickListener {
                movie?.let { onItemClickListener(it.id) }
            }
        }

        fun bind(movie: Movie) {
            this.movie = movie
            binding.image.loadImage(url = movie.imageUrl, contentDescription = movie.title)
        }

        companion object {
            fun from(
                parent: ViewGroup,
                onItemClickListener: (movieId: String) -> Unit
            ): ViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ListItemMovieBinding.inflate(inflater, parent, false)
                return ViewHolder(binding, onItemClickListener)
            }
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean =
            oldItem == newItem
    }

}