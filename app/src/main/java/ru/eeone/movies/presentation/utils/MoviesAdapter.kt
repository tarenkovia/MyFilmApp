package ru.eeone.movies.presentation.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DiffUtil.calculateDiff
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.eeone.movies.R
import ru.eeone.movies.data.model.Movie
import ru.eeone.movies.databinding.ItemMovieBinding

class MoviesAdapter :
    RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {

    private class DiffCallback(
        private val old: List<Movie>,
        private val new: List<Movie>
    ) :
        DiffUtil.Callback() {
        override fun getOldListSize() = old.size

        override fun getNewListSize() = new.size

        override fun areItemsTheSame(oldIndex: Int, newIndex: Int) =
            old[oldIndex].title == new[newIndex].title


        override fun areContentsTheSame(oldIndex: Int, newIndex: Int) =
            old[oldIndex] == new[newIndex]
    }

    private var items = mutableListOf<Movie>()

    var onItemClick: ((Movie) -> Unit)? = null

    fun setItems(items: List<Movie>) {
        val diffResult = calculateDiff(DiffCallback(this.items, items))
        this.items.apply {
            clear()
            addAll(items)
        }
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding =
            ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    inner class MovieViewHolder(private var binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            itemView.setOnClickListener {
                onItemClick?.invoke(movie)
            }

            binding.apply {
                textViewTitle.text = movie.title
                textViewYear.text = movie.year
                if (movie.imDbRating.isNullOrBlank()) {
                    textViewRating.text =
                        root.context.getString(R.string.rating_placeholder, "-")
                } else {
                    textViewRating.text =
                        root.context.getString(R.string.rating_placeholder, movie.imDbRating)
                }
                Glide.with(root.context)
                    .load(movie.image)
                    .into(imageViewPoster)
            }
        }
    }
}
