package ru.eeone.movies.presentation.popular

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.eeone.movies.databinding.FragmentPopularMoviesBinding
import ru.eeone.movies.presentation.main.MainActivity
import ru.eeone.movies.presentation.utils.MoviesAdapter
import ru.eeone.movies.presentation.utils.ResponseWrapper

@AndroidEntryPoint
class PopularMoviesFragment : Fragment() {
    private var _binding: FragmentPopularMoviesBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<PopularMoviesViewModel>()

    private val adapter = MoviesAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPopularMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerViewMovies.adapter = adapter

        lifecycleScope.launch {
            viewModel.getPopularMovies()
        }

        adapter.onItemClick = {
            findNavController().navigate(
                PopularMoviesFragmentDirections.actionPopularMoviesFragmentToMovieDetailsFragment(
                    it
                )
            )
        }

        viewModel.moviesLiveData.observe(viewLifecycleOwner) { result ->
            when (result) {
                is ResponseWrapper.Loading -> {
                    (requireActivity() as MainActivity).showProgressBar()
                }

                is ResponseWrapper.Error -> {
                    (requireActivity() as MainActivity).hideProgressBar()
                    Toast.makeText(requireContext(), result.errorMessage, Toast.LENGTH_SHORT).show()
                }

                is ResponseWrapper.Success -> {
                    (requireActivity() as MainActivity).hideProgressBar()
                    adapter.setItems(result.movie ?: listOf())
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}