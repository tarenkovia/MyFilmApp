package ru.eeone.movies.presentation.detail

import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import ru.eeone.movies.R
import ru.eeone.movies.data.model.Movie
import ru.eeone.movies.databinding.FragmentMovieDetailsBinding

class MovieDetailsFragment : Fragment() {
    private var _binding: FragmentMovieDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val movie = arguments?.parcelable<Movie>("movie")!!

        binding.toolbar.title = movie.fullTitle
        binding.textViewCrew.text = movie.crew
        binding.textViewRating.text = getString(R.string.rating_placeholder, movie.imDbRating)
        binding.textViewYear.text = movie.year
        binding.textViewRankCount.text = movie.rank ?: "undefined"
        binding.textViewRankUpDown.text = movie.rankUpDown ?: "undefined"
        binding.textViewRatingCount.text = movie.imDbRatingCount ?: "undefined"
        Glide.with(requireContext()).load(movie.image).into(binding.imageViewPoster)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

inline fun <reified T : Parcelable> Bundle.parcelable(key: String): T? = when {
    SDK_INT >= 33 -> getParcelable(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelable(key) as? T
}
